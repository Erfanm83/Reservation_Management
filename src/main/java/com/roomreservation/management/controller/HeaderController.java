package com.roomreservation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderController {

    @Autowired
    @Qualifier("userController")
    private UserController userController;

    @Autowired
    @Qualifier("adminController")
    private AdminController adminController;

    @GetMapping("/handle-request")
    public String handleRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_USER".equals(role)) {
            return userController.handleRequest();
        } else if ("ROLE_ADMIN".equals(role)) {
            return adminController.handleRequest();
        }

        return "Unknown role";
    }
}

