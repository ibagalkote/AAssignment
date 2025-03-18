package com.apple.weather.app.exceptions;

@SuppressWarnings("serial")
public class InvalidInputWeatherAppException extends RuntimeException{

    public InvalidInputWeatherAppException(String message) {
        super(message);
    }
}
