package com.oocl.todolist.controller;

import com.oocl.todolist.service.TaskService;
import com.oocl.todolist.vo.TaskVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "")
    public List<TaskVo> findAll() {
        return taskService.findAll();
    }
}
