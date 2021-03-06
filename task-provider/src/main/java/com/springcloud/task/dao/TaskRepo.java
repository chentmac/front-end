package com.springcloud.task.dao;

import java.util.List;

import com.springcloud.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepo extends JpaRepository<Task, Long> {
  @Modifying
  @Query(value = "delete from Task t where t.id=?1")
  void deleteTask(long taskId);


  Task findById(long id);

  @Query(value = "select * from task t,task_assign a where t.id = a.task_id and t.title = ?1",nativeQuery = true)
  List<Task> findByExecutor(String executor);

  @Query(value = "select * from task t,task_assign a where t.id = a.task_id and a.is_completed = 0 ",nativeQuery = true)
  List<Task> findAllToDo();

  @Query(value = "select * from task t,task_assign a where t.id = a.task_id and a.is_completed = 1",nativeQuery = true)
  List<Task> findAllFinished();
}
