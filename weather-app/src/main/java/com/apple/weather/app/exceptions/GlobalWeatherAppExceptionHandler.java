package com.apple.weather.app.exceptions;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apple.weather.app.exceptions.dto.WeatherAppErrorResponse;

@ControllerAdvice
public class GlobalWeatherAppExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalWeatherAppExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundWeatherAppException.class)
	public ResponseEntity<WeatherAppErrorResponse> handleResourceNotFound(ResourceNotFoundWeatherAppException ex) {
        logger.warn("Resource Not Found: {}", ex.getMessage());

		return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidInputWeatherAppException.class)
	public ResponseEntity<WeatherAppErrorResponse> handleInvalidInput(InvalidInputWeatherAppException ex) {
        logger.warn("Invalid Input: {}", ex.getMessage());

		return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WeatherServiceWeatherAppException.class)
	public ResponseEntity<WeatherAppErrorResponse> handleWeatherServiceException(WeatherServiceWeatherAppException ex) {
        logger.error("Weather Service Error: {}", ex.getMessage(), ex);
		
		return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(WeatherRepositoryWeatherAppException.class)
	public ResponseEntity<WeatherAppErrorResponse> handleWeatherRepositoryException(
			WeatherRepositoryWeatherAppException ex) {
        logger.error("Weather Repository Error: {}", ex.getMessage(), ex);
		
		return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<WeatherAppErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected Error: {}", ex.getMessage(), ex);

		return createErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<WeatherAppErrorResponse> createErrorResponse(String message, HttpStatus status) {

		WeatherAppErrorResponse errorResponse = WeatherAppErrorResponse.builder().timestamp(LocalDateTime.now())
				.message(message).status(status.value()).error(status.getReasonPhrase()).build();
        logger.debug("Error Response: {}", errorResponse);
		return new ResponseEntity<>(errorResponse, status);
	}
}
