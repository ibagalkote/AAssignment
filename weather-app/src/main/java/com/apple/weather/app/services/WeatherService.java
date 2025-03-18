package com.apple.weather.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.apple.weather.app.adapters.secondary.WeatherLocationRepository;
import com.apple.weather.app.domain.WeatherLocation;
import com.apple.weather.app.dto.WeatherLocationDTO;
import com.apple.weather.app.exceptions.WeatherServiceWeatherAppException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WeatherService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

	@Autowired
	private WeatherLocationRepository weatherLocationRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Cacheable(value = "weather", key = "#zipCode")
	public Optional<WeatherLocationDTO> getWeatherByZipCode(String zipCode) {
		logger.info("Fetching weather data for zip code: {}", zipCode);
		try {
			WeatherLocation weatherLocation = weatherLocationRepository.safeFindByZipCode(zipCode);
			WeatherLocationDTO weatherLocationDTO = modelMapper.map(weatherLocation, WeatherLocationDTO.class);
			return Optional.ofNullable(weatherLocationDTO);
		} catch (Exception e) {
            logger.error("Error fetching weather data for zip code: {}", zipCode, e);
			throw new WeatherServiceWeatherAppException("Error retrieving weather by zipcode", e);
		}
	}

	@Transactional
	public Optional<List<WeatherLocationDTO>> getAllWeatherLocations() {
		try {
			List<WeatherLocation> weatherLocations = weatherLocationRepository.findAll();
			List<WeatherLocationDTO> weatherLocationDTOs = weatherLocations.stream()
					.map(weather -> modelMapper.map(weatherLocations, WeatherLocationDTO.class))
					.collect(Collectors.toList());
			return Optional.ofNullable(weatherLocationDTOs);
		} catch (Exception e) {
			throw new WeatherServiceWeatherAppException("Error retrieving all weather locations", e);
		}
	}
}
