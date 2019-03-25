package com.springcloud.task;

import java.util.List;

public interface UserService {
    List<UserVo> getAllUserInfo();
    boolean save(UserVo userVo);
    User login(LoginVo loginVo);
    User findById(long id);
}
