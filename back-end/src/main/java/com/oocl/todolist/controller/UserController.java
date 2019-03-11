package com.oocl.todolist.controller;

import com.oocl.todolist.entity.User;
import com.oocl.todolist.service.UserService;
import com.oocl.todolist.vo.LoginVo;
import com.oocl.todolist.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findAll")
    public List<UserVo> findAll() {
        return userService.getAllUserInfo();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean save(@RequestBody UserVo userVo) {
        boolean result = userService.save(userVo);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

}
