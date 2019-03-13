package com.oocl.todolist.dao;

import com.oocl.todolist.entity.TaskAssign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskAssignRepo extends JpaRepository<TaskAssign, Long> {
  TaskAssign findByUsernameAndTaskId(String username,Long taskId);

  List<TaskAssign> findByTaskId(long taskId);

  void deleteByTaskId(long taskId);
}
