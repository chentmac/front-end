package com.oocl.todolist.translator;

import com.oocl.todolist.dao.TaskAssignRepo;
import com.oocl.todolist.dao.UserRepo;
import com.oocl.todolist.entity.Task;
import com.oocl.todolist.entity.TaskAssign;
import com.oocl.todolist.entity.User;
import com.oocl.todolist.vo.TaskVo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskTranslator {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskAssignRepo taskAssignRepo;

    public Task translateToEntity(TaskVo vo) {
        Task task = new Task();
        task.setTitle(vo.getTitle());
        task.setContent(vo.getContent());
        task.setExpireDate(vo.getExpireDate());
        User initiator = userRepo.findUserById(vo.getInitiatorId());
        task.setInitiator(initiator);
        List<Long> executorsId = vo.getExecutorsId();
        List<User> executors = userRepo.findAllById(executorsId);
        task.setExecutors(executors);
        return task;
    }

    public List<TaskVo> translateToVo(List<Task> tasks) {
        List<TaskVo> vos = new ArrayList<>();
        for (Task task : tasks) {
            long taskId = task.getId();
            TaskVo vo = new TaskVo();
            vo.setContent(task.getContent());
            vo.setTitle(task.getTitle());
            vo.setExpireDate(task.getExpireDate());
            vo.setInitiatorName(task.getInitiator().getUserName());
            List<String> usernames = new ArrayList<>();
            Map<String, Boolean> executorCompleteMap = new HashMap<>();
            for (User user : task.getExecutors()) {
                String username = user.getUserName();
                usernames.add(username);
                TaskAssign taskAssign = taskAssignRepo.findByUsernameAndTaskId(username, taskId);
                executorCompleteMap.put(taskAssign.getUsername(), taskAssign.isCompleted());
            }
            vo.setExecutorCompleteMap(executorCompleteMap);
            vo.setExecutorsName(usernames);
            vos.add(vo);
        }
        return vos;
    }

}
