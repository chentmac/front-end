package com.springcloud.task.controller;

import com.springcloud.task.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dunn
 */
@FeignClient(name= "USERPROVIDER")
public interface UserRemote {
  @GetMapping("/{id}")
  public User findUserById(@PathVariable(value="id") Long id);
}
