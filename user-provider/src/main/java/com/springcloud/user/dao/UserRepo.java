package com.springcloud.user.dao;

import com.springcloud.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
  User findUserByUserNameAndPassword(String userName, String password);

  User findUserById(long id);

  User findUserByUserName(String userName);
}
