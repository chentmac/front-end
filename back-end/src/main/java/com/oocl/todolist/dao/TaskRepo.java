package com.oocl.todolist.dao;

import com.oocl.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepo extends JpaRepository<Task, Long> {
  @Modifying
  @Query(value = "delete from Task t where t.id=?1")
  void deleteTask(long taskId);
}
