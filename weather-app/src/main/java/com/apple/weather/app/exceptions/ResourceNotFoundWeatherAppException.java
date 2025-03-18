package com.apple.weather.app.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundWeatherAppException extends RuntimeException {
	
	public ResourceNotFoundWeatherAppException(String message) {
		super(message);
	}
}
