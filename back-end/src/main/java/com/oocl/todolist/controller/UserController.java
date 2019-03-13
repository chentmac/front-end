package com.oocl.todolist.controller;

import com.oocl.todolist.entity.User;
import com.oocl.todolist.service.UserService;
import com.oocl.todolist.vo.LoginVo;
import com.oocl.todolist.vo.UserVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public List<UserVo> findAll() {
        return userService.getAllUserInfo();
    }

    @PostMapping
    public boolean save(@RequestBody UserVo userVo) {
        return userService.save(userVo);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

}
