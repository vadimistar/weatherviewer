package com.vadimistar.weatherviewer.exceptions;

public class UserAlreadyExistsException extends BadRequestException {
    public UserAlreadyExistsException() {
        super("user already exists");
    }
}
