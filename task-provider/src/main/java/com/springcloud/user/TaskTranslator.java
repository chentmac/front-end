package com.springcloud.user;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTranslator {



    @Autowired
    private TaskAssignRepo taskAssignRepo;


    public List<TaskVo> translateToVo(List<Task> tasks) {
        List<TaskVo> vos = new ArrayList<>();
        for (Task task : tasks) {
            long taskId = task.getId();
            TaskVo vo = new TaskVo();
            vo.setTaskId(taskId);
            vo.setContent(task.getContent());
            vo.setTitle(task.getTitle());
            vo.setExpireDate(task.getExpireDate());
//            vo.setInitiatorName(task.getInitiator().getUserName());
            List<String> usernames = new ArrayList<>();
            Map<String, Boolean> executorCompleteMap = new HashMap<>();
            for (TaskAssign ta : taskAssignRepo.findByTaskId(taskId)) {
                String username = ta.getUsername();
                usernames.add(username);
                executorCompleteMap.put(ta.getUsername(), ta.isCompleted());
            }
            vo.setExecutorCompleteMap(executorCompleteMap);
            vo.setExecutorsName(usernames);
            vos.add(vo);
        }
        return vos;
    }

  public Task translateToEntity(TaskVo taskVo) {
      return new Task();
  }
}
