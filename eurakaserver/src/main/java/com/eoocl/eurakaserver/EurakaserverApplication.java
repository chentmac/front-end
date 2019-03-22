package com.eoocl.eurakaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakaserverApplication {

  public static void main(String[] args) {
    SpringApplication.run(EurakaserverApplication.class, args);
  }

}
