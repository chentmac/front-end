package com.spring.eurakaservice_second;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakaserviceSecondApplication {

  public static void main(String[] args) {
    SpringApplication.run(EurakaserviceSecondApplication.class, args);
  }

}
