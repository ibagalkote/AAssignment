package com.apple.weather.app.exceptions;

@SuppressWarnings("serial")
public class WeatherRepositoryWeatherAppException extends RuntimeException {

	public WeatherRepositoryWeatherAppException(String message, Throwable cause) {
		super(message, cause);
	}
}
