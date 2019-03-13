package com.oocl.todolist.service.impl;

import com.oocl.todolist.dao.UserRepo;
import com.oocl.todolist.entity.User;
import com.oocl.todolist.translator.UserTranslator;
import com.oocl.todolist.vo.LoginVo;
import com.oocl.todolist.vo.UserVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
public class UserServiceImplTest {

  private UserServiceImpl userService = new UserServiceImpl();

  private UserRepo userRepo = mock(UserRepo.class);

  private UserTranslator userTranslator = mock(UserTranslator.class);

  @Before
  public void init(){
    setField(userService,"userRepo",userRepo);
    setField(userService,"userTranslator",userTranslator);
  }

  @Test
  public void get_all_user_when_serach_all_user() {
    when(userRepo.findAll()).thenReturn(mockUserList());
    List<UserVo> allUserInfo = userService.getAllUserInfo();
    assertThat(allUserInfo.size(),is(1));
  }

  @Test
  public void should_return_true_when_save_a_user_succuss() {
    when(userRepo.save(any())).thenReturn(new User());
    boolean falg = userService.save(mockUserVo());
    assertThat(falg,is(true));
  }


  @Test
  public void should_return_false_when_save_a_user_failed() {
    doThrow(new RuntimeException("save failed")).when(userRepo).save(any());
    boolean falg = userService.save(mockUserVo());
    assertThat(falg,is(false));
  }

  @Test
  public void should_return_true_when_login_succuss() {
    User user = new User();
    user.setId(1l);
    when(userRepo.findUserByUserNameAndPassword(anyString(),anyString())).thenReturn(user);
    LoginVo loginVo = new LoginVo();
    loginVo.setPassword("123123");
    loginVo.setUserName("123123");
    User login = userService.login(loginVo);
    assertThat(login,is(true));
  }

  @Test
  public void should_return_false_when_login_succuss() {
    when(userRepo.findUserByUserNameAndPassword(anyString(),anyString())).thenReturn(null);
    User login = userService.login(new LoginVo());
    assertThat(login,is(false));
  }

  @Test
  public void should_return_user_when_find_user_by_id_and_user_exit() {
  when(userRepo.findUserById(anyLong())).thenReturn(new User());
  User byId = userService.findById(1L);
  assertNotNull(byId);
  }

  @Test
  public void should_return_user_when_find_user_by_id_and_user_not_exit() {
    when(userRepo.findUserById(anyLong())).thenReturn(null);
    User byId = userService.findById(1L);
    assertNull(byId);
  }

  private UserVo mockUserVo() {
    UserVo userVo = new UserVo();
    userVo.setId(1);
    userVo.setUserName("asd");
    return userVo;
  }

  private List<User> mockUserList() {
    List<User> user = new ArrayList<>();
    User userVo = new User();
    user.add(userVo);
    return user;
  }


}
