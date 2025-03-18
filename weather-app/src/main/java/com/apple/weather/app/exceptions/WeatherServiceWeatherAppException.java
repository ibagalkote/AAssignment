package com.apple.weather.app.exceptions;

@SuppressWarnings("serial")
public class WeatherServiceWeatherAppException extends RuntimeException {

	public WeatherServiceWeatherAppException(String message, Throwable cause) {
		super(message, cause);
	}
}
