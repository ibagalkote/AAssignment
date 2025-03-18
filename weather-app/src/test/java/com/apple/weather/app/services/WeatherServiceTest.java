package com.apple.weather.app.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.apple.weather.app.adapters.secondary.WeatherLocationRepository;
import com.apple.weather.app.data.WeatherData;
import com.apple.weather.app.domain.WeatherLocation;
import com.apple.weather.app.dto.WeatherLocationDTO;
import com.apple.weather.app.exceptions.ResourceNotFoundWeatherAppException;
import com.apple.weather.app.exceptions.WeatherServiceWeatherAppException;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

	@Mock
	private WeatherLocationRepository weatherLocationRepository;

	private ModelMapper modelMapper;

	@InjectMocks
	private WeatherService weatherService;

	@BeforeEach
	void setUp() {
		modelMapper = new ModelMapper();
		weatherService = new WeatherService(weatherLocationRepository, modelMapper);
	}

	@Test
	void getWeatherByZipCode() {
		// When
		when(weatherLocationRepository.safeFindByZipCode(WeatherData.ZIPCODE))
				.thenReturn(WeatherData.getWeatherLocation1());

		Optional<WeatherLocationDTO> weatherLocation = weatherService.getWeatherByZipCode(WeatherData.ZIPCODE);
		// Then
		assertThat(weatherLocation).isNotNull();
	}

	@Test
	void testGetWeatherByZipCode_NotFound() {
		// Given
		when(weatherLocationRepository.safeFindByZipCode(WeatherData.ZIPCODE_NOTFOUND))
				.thenThrow(ResourceNotFoundWeatherAppException.class);

		// When & Then
		Exception exception = assertThrows(WeatherServiceWeatherAppException.class,
				() -> weatherService.getWeatherByZipCode(WeatherData.ZIPCODE_NOTFOUND));
		assertNotNull(exception.getMessage());
	}

	@Test
	void getAllWeatherLocations() {
		List<WeatherLocation> weatherLocations = List.of(WeatherData.getWeatherLocation1(),
				WeatherData.getWeatherLocation2());
		// When
		when(weatherLocationRepository.findAll()).thenReturn(weatherLocations);

		Optional<List<WeatherLocationDTO>> weatherLocationDTOs = weatherService.getAllWeatherLocations();
		// Then
		// Make sure to import assertThat from org.assertj.core.api package
		assertThat(weatherLocationDTOs).isNotNull();
		assertThat(weatherLocationDTOs.get().size()).isEqualTo(2);
	}
}
