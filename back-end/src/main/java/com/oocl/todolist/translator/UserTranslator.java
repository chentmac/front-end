package com.oocl.todolist.translator;

import com.oocl.todolist.entity.User;
import com.oocl.todolist.vo.UserVo;

public class UserTranslator {

    public UserVo translateToVo(User user) {
        UserVo vo = new UserVo();
        vo.setId(user.getId());
        vo.setUserName(user.getUserName());
        return vo;
    }

}
