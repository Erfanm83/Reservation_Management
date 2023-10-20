package com.roomreservation.management.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean(name = "UserController")
    @ConditionalOnRole("ROLE_USER") // Custom condition to check role
    public UserController userController() {
        return new UserController();
    }

    @Bean(name = "َAminController")
    @ConditionalOnRole("ROLE_ADMIN") // Custom condition to check role
    public AdminController adminController() {
        return new AdminController();
    }
}
