package com.springcloud.task.service;


import com.springcloud.task.dto.TaskVo;
import com.springcloud.task.dao.TaskAssignRepo;
import com.springcloud.task.entity.Task;
import com.springcloud.task.entity.TaskAssign;
import com.springcloud.task.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskTranslator {


  @Autowired
  private TaskAssignRepo taskAssignRepo;

  @Autowired
  private DiscoveryClient discoveryClient;

  @Autowired
  private RestTemplate restTemplate;


  public List<TaskVo> translateToVo(List<Task> tasks) {
    List<TaskVo> vos = new ArrayList<>();
    for (Task task : tasks) {
      long taskId = task.getId();
      TaskVo vo = new TaskVo();
      vo.setTaskId(taskId);
      vo.setContent(task.getContent());
      vo.setTitle(task.getTitle());
      vo.setExpireDate(task.getExpireDate());
      vo.setInitiatorName(task.getInitiator());
      List<String> usernames = new ArrayList<>();

      Map<String, Boolean> executorCompleteMap = new HashMap<>();
      ServiceInstance serviceInstance = discoveryClient.getInstances("USERPROVIDER").get(0);
      String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
      System.out.println(url);
      for (TaskAssign ta : taskAssignRepo.findByTaskId(taskId)) {
        System.out.println(ta.getUserId());
        User user = restTemplate.getForObject(url+ta.getUserId(), User.class);
        usernames.add(user.getUserName());
        executorCompleteMap.put(user.getUserName(), ta.isCompleted());
      }
            vo.setExecutorCompleteMap(executorCompleteMap);
            vo.setExecutorsName(usernames);
      vos.add(vo);
    }
    return vos;
  }

  public Task translateToEntity(TaskVo vo) {
    Task task = new Task();
    task.setTitle(vo.getTitle());
    task.setId(vo.getTaskId());
    task.setContent(vo.getContent());
    task.setExpireDate(vo.getExpireDate());
//    User initiator = userRepo.findUserByUserName(vo.getInitiatorName());
    task.setInitiator(vo.getInitiatorName());
    List<Long> executorsId = vo.getExecutorsId();
//    List<User> executors = userRepo.findAllById(executorsId);
//    user.setExecutors(executors);
    return task;

  }
}
