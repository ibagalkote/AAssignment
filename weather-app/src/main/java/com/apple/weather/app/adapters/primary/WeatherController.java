package com.apple.weather.app.adapters.primary;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apple.weather.app.dto.WeatherLocationDTO;
import com.apple.weather.app.exceptions.InvalidInputWeatherAppException;
import com.apple.weather.app.services.WeatherService;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api/weather")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@GetMapping("/locations")
	@Timed(value = "getWeatherLocations", description = "Time taken to process getWeatherLocations")
	public ResponseEntity<?> getWeatherLocations() {
		logger.info("Received request to fetch zipcodes all available weather locations ");

		try {
			Optional<List<WeatherLocationDTO>> weatherLocationOpt = weatherService.getAllWeatherLocations();
			return weatherLocationOpt.map(weatherLocation -> new ResponseEntity(weatherLocation, HttpStatus.OK))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No weather locations found"));
		} catch (Exception e) {
			logger.error("Error retrieving zipcodes of all weather locations", e);
			throw e;
		}
	}

	@GetMapping("/locations/{zipCode}")
	@Timed(value = "getWeatherByZipCode", description = "Time taken to process hello getWeatherByZipCode")
	public ResponseEntity<?> getWeatherByZipCode(@PathVariable String zipCode) {
		logger.info("Received request for weather data for zip code: {}", zipCode);

		if (zipCode.length() < 5) {
			logger.warn("Invalid Zip Code provided: {}", zipCode);
			throw new InvalidInputWeatherAppException("Invalid Zip Code: Must be at least 5 characters");
		}
		try {
			Optional<WeatherLocationDTO> weatherLocationOpt = weatherService.getWeatherByZipCode(zipCode);
			logger.info("Successfully retrieved weather data for zip code: {}", zipCode);
			return weatherLocationOpt.map(weatherLocation -> new ResponseEntity(weatherLocation, HttpStatus.OK))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("Weather data not found for ZIP Code: " + zipCode));
		} catch (Exception e) {
			logger.error("Error retrieving weather data for zip code: {}", zipCode, e);
			throw e;
		}
	}
}
