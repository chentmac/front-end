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

    @PostMapping(value = "")
    public boolean save(@RequestBody TaskVo taskVo) {
      return taskService.save(taskVo);
    }

    @PutMapping(value = "")
    public boolean update(@RequestBody TaskVo taskVo) {
      return taskService.update(taskVo);

    }

    @GetMapping("")
    public List<TaskVo> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/todo/{username}")
    public List<TaskVo> findAllToDo(@PathVariable("username") String username){
        return taskService.findAllToDo(username);
    }

  @GetMapping("/finished")
  public List<TaskVo> findAllFinished(){
    return taskService.findAllFinished();
  }

    @DeleteMapping
    public boolean delete(@RequestBody List<TaskVo> taskVos){
        return taskService.delete(taskVos);
    }

    @GetMapping("/{executor}")
    public List<TaskVo> findByExecutor(@PathVariable("executor")String executor){
      return taskService.findByExecutor(executor);
    }

    @PutMapping("/finished/{username}/{taskId}")
    public boolean finishTask(@PathVariable("username") String username,@PathVariable("taskId") long taskId){
      return taskService.finishTask(username,taskId);
    }

}
