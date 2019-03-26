package com.springcloud.task;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public List<UserVo> findAll() {
    return userService.getAllUserInfo();
  }

  @PostMapping
  public boolean save(@RequestBody UserVo userVo) {
    return userService.save(userVo);

  }

  @HystrixCommand(fallbackMethod = "loginFallback")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public User login(@RequestBody LoginVo loginVo) {

    User user = userService.login(loginVo);
    if(user == null){
      throw new RuntimeException("fail");
    }
    return user;
  }

  @GetMapping("/{id}")
  public  User findUserById(@PathVariable Long id){
       return userService.findById(id);
  }

  public User loginFallback(@RequestBody LoginVo loginVo) {
    return new User();
  }

}
