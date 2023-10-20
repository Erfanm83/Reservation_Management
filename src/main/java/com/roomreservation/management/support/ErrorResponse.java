package com.roomreservation.management.support;

public class ErrorResponse {

    private String message;

    private ErrorResponse() {
        this("");
    }

    private ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse error(String message) {
        return new ErrorResponse(message);
    }

    public String getMessage() {
        return message;
    }
}