package com.oocl.todolist.dao;

import com.oocl.todolist.entity.Task;
import com.oocl.todolist.vo.TaskVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.validation.Validation;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
  @Modifying
  @Query(value = "delete from Task t where t.id=?1")
  void deleteTask(long taskId);

  @Query(value = "select * from task t,task_assign a where t.id = a.task_id and a.username = ?1",nativeQuery = true)
  List<Task> findByExecutor(String executor);

  @Query(value = "select * from task t,task_assign a where t.id = a.task_id and a.is_completed = 0 and a.username = ?1",nativeQuery = true)
  List<Task> findAllToDo(String username);
}
