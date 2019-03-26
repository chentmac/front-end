package com.springcloud.user;


import java.util.List;

public interface TaskService {
  boolean save(TaskVo taskVo);

  List<TaskVo> findAll();

  boolean update(TaskVo taskVo);

  boolean delete(List<TaskVo> taskVos);

  List<TaskVo> findByExecutor(String executor);

  List<TaskVo> findAllToDo();

  boolean finishTask(String username, long taskId);

  List<TaskVo> findAllFinished();
}
