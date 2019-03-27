package com.springcloud.user.service;

import com.springcloud.user.entity.User;
import com.springcloud.user.dto.UserVo;
import org.springframework.stereotype.Component;

@Component
public class UserTranslator {

    public UserVo translateToVo(User user) {
        UserVo vo = new UserVo();
        vo.setId(user.getId());
        vo.setUserName(user.getUserName());
        return vo;
    }

    public User translateToEntity(UserVo userVo) {
      User user = new User();
      user.setUserName(userVo.getUserName());
      user.setPassword(userVo.getPassword());
      return user;
    }

}
