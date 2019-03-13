package com.oocl.todolist.controller;

import com.oocl.todolist.service.TaskService;
import com.oocl.todolist.vo.TaskVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(value = "update")
    public boolean update(@RequestBody TaskVo taskVo) {
      boolean result = taskService.update(taskVo);
      return result;
    }

    @GetMapping("/findAll")
    public List<TaskVo> findAll() {
        return taskService.findAll();
    }

    @DeleteMapping
    public boolean delete(@RequestBody List<TaskVo> taskVos){
        return taskService.delete(taskVos);
    }

}
