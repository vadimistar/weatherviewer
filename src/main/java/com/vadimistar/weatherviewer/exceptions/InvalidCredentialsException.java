package com.vadimistar.weatherviewer.exceptions;

public class InvalidCredentialsException extends BadRequestException {
    public InvalidCredentialsException() {
        super("invalid credentials");
    }
}
