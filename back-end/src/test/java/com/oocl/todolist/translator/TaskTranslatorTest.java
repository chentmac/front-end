package com.oocl.todolist.translator;

import com.oocl.todolist.dao.UserRepo;
import com.oocl.todolist.entity.Task;
import com.oocl.todolist.entity.User;
import com.oocl.todolist.vo.TaskVo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;



public class TaskTranslatorTest {
  private TaskTranslator taskTranslator = new TaskTranslator();
  private UserRepo userRepo = mock(UserRepo.class);

  @Before
  public void init(){
    setField(taskTranslator,"userRepo",userRepo);
  }

  @Test
  public void should_return_task_when_get_a_task_vo() {
    TaskVo vo = mockTaskVo();
    when(userRepo.findUserById(anyLong())).thenReturn(new User());
    when(userRepo.findAllById(anyList())).thenReturn(new ArrayList<User>());
    Task task = taskTranslator.translateToEntity(vo);

    assertNotNull(task);
    assertThat(task.getContent(),is("1"));
    assertThat(task.getTitle(),is("3"));
  }

  @Test
  public void should_return_taskVo_list_when_given_task_list() {
    List<Task> tasks = mockTaskList();
    List<TaskVo> taskVos = taskTranslator.translateToVo(tasks);
    assertTrue(taskVos.size()>0);
  }

  private List<Task> mockTaskList() {
    List<Task> task = new ArrayList<>();
    Task task1 = new Task();
    User user = new User();
    user.setUserName("1");
    task1.setInitiator(user);
    List<User> users = new ArrayList<>();
    users.add(user);
    task1.setExecutors(users);
    task.add(task1);
    return task;
  }

  private TaskVo mockTaskVo() {
    TaskVo taskVo = new TaskVo();
    taskVo.setContent("1");
    taskVo.setExecutorsName(mockExecutorsName());
    taskVo.setInitiatorName("2");
    taskVo.setTitle("3");
    taskVo.setExecutorsId(mockExecutorsIds());
    taskVo.setInitiatorId(4);
    return taskVo;
  }

  private List<String> mockExecutorsName() {
    List<String> strings = new ArrayList<>();
    strings.add("ss");
    return strings;
  }

  private List<Long> mockExecutorsIds() {
    List<Long> ids = new ArrayList<>();
    ids.add(1l);
    return ids;
  }
}
