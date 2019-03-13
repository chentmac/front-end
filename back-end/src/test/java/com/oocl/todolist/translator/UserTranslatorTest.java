package com.oocl.todolist.translator;

import com.oocl.todolist.entity.User;
import com.oocl.todolist.vo.UserVo;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserTranslatorTest {
  private UserTranslator userTranslator = new UserTranslator();

  @Test
  public void should_return_uservo_when_input_user() {
    User user = new User();
    user.setUserName("1");
    user.setId(1l);
    UserTranslator userTranslator = new UserTranslator();
    UserVo userVo = userTranslator.translateToVo(user);
    assertThat(userVo.getPassword(),is(user.getPassword()));
    assertThat(userVo.getUserName(),is(user.getUserName()));
  }

  @Test
  public void should_return_user_when_input_user_vo() {
    UserVo userVo = new UserVo();
    userVo.setUserName("1");
    userVo.setPassword("2");
    User user = userTranslator.translateToEntity(userVo);
    assertThat(user.getUserName(),is(userVo.getUserName()));
    assertThat(user.getPassword(),is(userVo.getPassword()));
  }
}
