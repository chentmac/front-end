package com.oocl.cloudzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy //开启网关Zuul
@EnableEurekaClient
public class CloudZuulApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudZuulApplication.class, args);
  }

}
