package com.springcloud.task.dao;

import java.util.List;

import com.springcloud.task.entity.TaskAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface TaskAssignRepo extends JpaRepository<TaskAssign, Long> {

  List<TaskAssign> findByTaskId(long taskId);

  @Modifying
  void deleteByTaskId(long taskId);

  @Modifying
  @Query(value = "update task_assign set is_completed = 1 where user_id = ?1 and task_id = ?2",nativeQuery = true)
  void finishTask(String username, long taskId);
}
