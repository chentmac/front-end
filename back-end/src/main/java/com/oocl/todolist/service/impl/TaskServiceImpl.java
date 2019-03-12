package com.oocl.todolist.service.impl;

import com.oocl.todolist.dao.TaskAssignRepo;
import com.oocl.todolist.dao.TaskRepo;
import com.oocl.todolist.entity.Task;
import com.oocl.todolist.entity.TaskAssign;
import com.oocl.todolist.entity.User;
import com.oocl.todolist.service.TaskService;
import com.oocl.todolist.translator.TaskTranslator;
import com.oocl.todolist.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskAssignRepo taskAssignRepo;

    @Autowired
    private TaskTranslator translator;

    @Override
    public boolean save(TaskVo taskVo) {
        try {
            Task task = translator.translateToEntity(taskVo);
            Task t = taskRepo.save(task);
            insertAssign(t);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<TaskVo> findAll() {
        List<Task> tasks = taskRepo.findAll();
        List<TaskVo> vos = translator.translateToVo(tasks);
        return vos;
    }

    private void insertAssign(Task t) {
        List<User> users = t.getExecutors();
        long taskId = t.getId();
        List<TaskAssign> assigns = new ArrayList<>();
        for (User u : users) {
            TaskAssign a = new TaskAssign();
            a.setTaskId(taskId);
            a.setUsername(u.getUserName());
            a.setCompleted(false);
            assigns.add(a);
        }
        taskAssignRepo.saveAll(assigns);
    }
}
