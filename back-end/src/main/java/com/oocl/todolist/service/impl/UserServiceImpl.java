package com.oocl.todolist.service.impl;

import com.oocl.todolist.dao.UserRepo;
import com.oocl.todolist.entity.User;
import com.oocl.todolist.service.UserService;
import com.oocl.todolist.translator.UserTranslator;
import com.oocl.todolist.vo.LoginVo;
import com.oocl.todolist.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserTranslator userTranslator;

    @Override
    public List<UserVo> getAllUserInfo() {
        List<User> users =  userRepo.findAll();
        List<UserVo> vos = new ArrayList<>();
        UserTranslator translator = new UserTranslator();
        for (User u : users) {
            UserVo vo = translator.translateToVo(u);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public boolean save(UserVo userVo) {
        try {
            User user = userTranslator.translateToEntity(userVo);
            userRepo.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public User login(LoginVo loginVo) {
        User result = userRepo.findUserByUserNameAndPassword(loginVo.getUserName(), loginVo.getPassword());
        return result;
    }

    @Override
    public User findById(long id) {
        return userRepo.findUserById(id);
    }
}
