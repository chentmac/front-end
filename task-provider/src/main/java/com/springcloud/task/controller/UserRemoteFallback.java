package com.springcloud.task.controller;

import com.springcloud.task.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class UserRemoteFallback implements UserRemote {
    @Override
    public User findUserById(@PathVariable(value = "id") Long id) {
        User user = new User();
        user.setUserName("System fallback user");
        return user;
    }
}
