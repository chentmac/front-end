package com.oocl.todolist.dao;

import com.oocl.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
