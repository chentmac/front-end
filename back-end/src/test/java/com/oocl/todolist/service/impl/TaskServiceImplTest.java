package com.oocl.todolist.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.oocl.todolist.dao.TaskAssignRepo;
import com.oocl.todolist.dao.TaskRepo;
import com.oocl.todolist.entity.Task;
import com.oocl.todolist.entity.User;
import com.oocl.todolist.translator.TaskTranslator;
import com.oocl.todolist.vo.TaskVo;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TaskServiceImplTest {

  TaskServiceImpl taskService;

  TaskRepo taskRepo;

  TaskAssignRepo taskAssignRepo;

  TaskTranslator taskTranslator;


  @Before
  public void init() {
    taskService = new TaskServiceImpl();
    taskRepo = mock(TaskRepo.class);
    taskAssignRepo = mock(TaskAssignRepo.class);
    taskTranslator = mock(TaskTranslator.class);
    setField(taskService, "taskRepo", taskRepo);
    setField(taskService, "taskAssignRepo", taskAssignRepo);
    setField(taskService, "translator", taskTranslator);

  }

  @Test
  public void should_return_not_null_when_find_all_task() {
    when(taskRepo.findAll()).thenReturn(Collections.singletonList(new Task()));
    when(taskTranslator.translateToVo(anyList())).thenReturn(Collections.singletonList(new TaskVo()));

    List<TaskVo> all = taskService.findAll();

    assertNotNull(all);

  }

  @Test
  public void should_be_save_when_insert_assigns() {
    Task task = new Task();
    task.setExecutors(Collections.singletonList(new User()));
    when(taskTranslator.translateToEntity(any())).thenReturn(new Task());
    when(taskRepo.save(any())).thenReturn(task);

    taskService.save(new TaskVo());

    verify(taskAssignRepo, times(1)).saveAll(anyList());
  }


  @Test
  public void should_return_false_when_save_given_empty_user() {
    boolean result = taskService.save(null);

    assertFalse(result);
  }
}
