package com.vadimistar.weatherviewer.exceptions;

public class OpenWeatherApiException extends RuntimeException {
    public OpenWeatherApiException(String message) {
        super("open weather api: " + message);
    }
}
