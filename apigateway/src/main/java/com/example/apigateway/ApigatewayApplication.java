package com.example.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import javax.validation.Valid;

@SpringBootApplication
public class ApigatewayApplication {

  @Value("${monolit:http://localhost:8080/}")
  private String monolit;

  public static void main(String[] args) {
    SpringApplication.run(ApigatewayApplication.class, args);
  }

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(p -> p
            .path("/*")
            .uri(monolit))
        .build();
  }
}
