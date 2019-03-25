package com.springcloud.user;

import com.oocl.todolist.entity.User;
import com.oocl.todolist.service.TaskService;
import com.oocl.todolist.translator.TaskTranslator;
import com.oocl.todolist.vo.TaskVo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      insertAssign(task);
      taskRepo.save(task);
    } catch (Exception e) {
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

  @Transactional
  @Override
  public boolean update(TaskVo taskVo) {
    try {
      Task task = translator.translateToEntity(taskVo);
      deleteAssign(task.getId());
      insertAssign(task);
      taskRepo.save(task);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Transactional
  @Override
  public boolean delete(List<TaskVo> taskVos) {
    for (TaskVo t : taskVos) {
      Task task = taskRepo.findById(t.getTaskId());
      taskRepo.delete(task);
      taskRepo.deleteTask(t.getTaskId());
      deleteAssign(t.getTaskId());
    }
    return true;
  }

  @Override
  public List<TaskVo> findByExecutor(String executor) {
    List<Task> tasks = taskRepo.findByExecutor(executor);
    List<TaskVo> vos = translator.translateToVo(tasks);
    return vos;
  }

  @Override
  public List<TaskVo> findAllToDo(String username) {
    List<Task> tasks = taskRepo.findAllToDo(username);
    List<TaskVo> vos = translator.translateToVo(tasks);
    return vos;
  }

  @Transactional
  @Override
  public boolean finishTask(String username, long taskId) {
    taskAssignRepo.finishTask(username,taskId);
    return true;
  }

  @Override
  public List<TaskVo> findAllFinished() {
    List<Task> tasks = taskRepo.findAllFinished();
    List<TaskVo> vos = translator.translateToVo(tasks);
    return vos;
  }


  private void deleteAssign(long taskId) {
    taskAssignRepo.deleteByTaskId(taskId);
  }

  private void insertAssign(Task t) {
    List<User> users = t.getExecutors();
    List<TaskAssign> assigns = new ArrayList<>();
    for (User u : users) {
      TaskAssign a = new TaskAssign();
      a.setTask(t);
      a.setUsername(u.getUserName());
      a.setCompleted(false);
      assigns.add(a);
    }
    t.setTaskAssigns(assigns);
  }
}
