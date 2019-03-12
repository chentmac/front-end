package com.oocl.todolist.dao;

import com.oocl.todolist.entity.TaskAssign;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskAssignRepo extends JpaRepository<TaskAssign, Long> {
  TaskAssign findByUsernameAndTaskId(String username,Long taskId);

}
