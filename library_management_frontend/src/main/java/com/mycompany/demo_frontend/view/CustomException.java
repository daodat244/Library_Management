package com.mycompany.demo_frontend.view;

public class CustomException extends Exception {
    private final int statusCode;

    public CustomException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
