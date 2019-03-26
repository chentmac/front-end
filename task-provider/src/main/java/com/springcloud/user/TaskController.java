package com.springcloud.user;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public boolean save(@RequestBody TaskVo taskVo) {
      return taskService.save(taskVo);
    }

    @PutMapping
    public boolean update(@RequestBody TaskVo taskVo) {
      return taskService.update(taskVo);

    }

    @GetMapping
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
