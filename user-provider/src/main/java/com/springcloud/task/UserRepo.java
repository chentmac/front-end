package com.springcloud.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
  User findUserByUserNameAndPassword(String userName, String password);

  User findUserById(long id);

  User findUserByUserName(String userName);
}
