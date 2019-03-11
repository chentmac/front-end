package com.oocl.todolist.service;

import com.oocl.todolist.entity.User;
import com.oocl.todolist.vo.LoginVo;
import com.oocl.todolist.vo.UserVo;

import java.util.List;

public interface UserService {
    List<UserVo> getAllUserInfo();
    boolean save(UserVo userVo);
    boolean login(LoginVo loginVo);
    User findById(long id);
}
