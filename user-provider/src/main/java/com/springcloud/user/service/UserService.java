package com.springcloud.user.service;

import com.springcloud.user.entity.User;
import com.springcloud.user.dto.UserVo;
import com.springcloud.user.dto.LoginVo;

import java.util.List;

public interface UserService {
    List<UserVo> getAllUserInfo();
    boolean save(UserVo userVo);
    User login(LoginVo loginVo);
    User findById(long id);
}
