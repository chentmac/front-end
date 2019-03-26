package com.springcloud.task;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
