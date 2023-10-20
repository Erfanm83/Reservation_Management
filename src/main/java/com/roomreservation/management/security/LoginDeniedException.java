package com.roomreservation.management.security;

public class LoginDeniedException extends RuntimeException {

    public LoginDeniedException(String message) {
        super(message);
    }
}
