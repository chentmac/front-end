package com.oocl.todolist.dao;

import com.oocl.todolist.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserRepoTest {

  @Autowired
  private UserRepo userRepo;

  @Test
  public void should_return_user_when_input_correct_username_and_password() {
    User user = new User();
    user.setUserName("avc");
    user.setPassword("123");
    user.setDepartment("cba");
    userRepo.save(user);
    User avc = userRepo.findUserByUserNameAndPassword("avc", "123");
    assert(avc!=null);
    assertThat(avc.getUserName(),is("avc"));
    assertThat(avc.getPassword(),is("123"));
  }

  @Test
  public void should_return_user_when_input_defect_username_and_password() {
    User user = new User();
    user.setUserName("avc");
    user.setPassword("123");
    user.setDepartment("cba");
    userRepo.save(user);
    User avc = userRepo.findUserByUserNameAndPassword("avc", "1223");
    assert(avc==null);
  }

  @Test
  public void should_return_user_when_input_Id() {
    User user = new User();
    user.setUserName("avc");
    user.setPassword("123");
    user.setDepartment("cba");
    userRepo.save(user);
    User avc = userRepo.findUserById(user.getId());
    assert(avc!=null);
    assertThat(avc.getUserName(),is("avc"));
  }


  @Test
  public void should_return_user_when_input_unexit_Id() {
    User avc = userRepo.findUserById(901238l);
    assert(avc==null);
  }
}
