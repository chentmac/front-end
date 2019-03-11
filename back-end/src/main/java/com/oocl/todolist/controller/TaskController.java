package com.oocl.todolist.controller;

import com.oocl.todolist.entity.Task;
import com.oocl.todolist.entity.User;
import com.oocl.todolist.service.TaskService;
import com.oocl.todolist.service.UserService;
import com.oocl.todolist.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean save(@RequestBody TaskVo taskVo) {
        boolean result = taskService.save(taskVo);
        return result;
    }

    @RequestMapping(value = "/findAll")
    public List<TaskVo> findAll() {
        return taskService.findAll();
    }
}
