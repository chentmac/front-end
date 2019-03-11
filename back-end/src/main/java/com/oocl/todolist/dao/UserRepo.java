package com.oocl.todolist.dao;

import com.oocl.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUserNameAndPassword(String userName, String password);
    User findUserById(long id);
}
