package com.apple.weather.app.adapters.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.apple.weather.app.data.WeatherData;
import com.apple.weather.app.domain.WeatherLocation;
import com.apple.weather.app.exceptions.WeatherRepositoryWeatherAppException;

@ExtendWith(MockitoExtension.class)
public class WeatherLocationRepositoryTest {

	@Mock
	private WeatherLocationRepository weatherLocationRepository;

	@InjectMocks
	private WeatherLocationRepositoryTest injectedRepositoryTest;

	private WeatherLocation weatherLocation;

	@BeforeEach
	void setUp() {
		weatherLocation = WeatherData.getWeatherLocation1();
	}

	@Test
	void testFindByZipCode_Success() {
		// Given
		when(weatherLocationRepository.findByZipCode(WeatherData.ZIPCODE)).thenReturn(weatherLocation);

		// When
		WeatherLocation result = weatherLocationRepository.findByZipCode(WeatherData.ZIPCODE);

		// Then
		assertNotNull(result);
		assertEquals(WeatherData.ZIPCODE, result.getZipCode());
		assertEquals(WeatherData.CITY, result.getCity());
		verify(weatherLocationRepository, times(1)).findByZipCode(WeatherData.ZIPCODE);
	}

	@Test
	void testFindByZipCode_NotFound() {
		// Given
		when(weatherLocationRepository.findByZipCode(WeatherData.ZIPCODE_NOTFOUND)).thenReturn(null);

		// When
		WeatherLocation result = weatherLocationRepository.findByZipCode(WeatherData.ZIPCODE_NOTFOUND);

		// Then
		assertNull(result);
		verify(weatherLocationRepository, times(1)).findByZipCode(WeatherData.ZIPCODE_NOTFOUND);
	}

	@Test
	void testSafeFindByZipCode_Success() {
		// Given
		when(weatherLocationRepository.findByZipCode(WeatherData.ZIPCODE)).thenReturn(weatherLocation);

		// Allow the default method to be executed
		doCallRealMethod().when(weatherLocationRepository).safeFindByZipCode(WeatherData.ZIPCODE);
		// When
		WeatherLocation result = weatherLocationRepository.safeFindByZipCode(WeatherData.ZIPCODE);

		// Then
		assertNotNull(result);
		assertEquals(WeatherData.ZIPCODE, result.getZipCode());
		assertEquals(WeatherData.CITY, result.getCity());
		verify(weatherLocationRepository, times(1)).findByZipCode(WeatherData.ZIPCODE);
	}

	@Test
	void testSafeFindByZipCode_NotFound() {
		// Given
		when(weatherLocationRepository.findByZipCode(WeatherData.ZIPCODE_NOTFOUND)).thenReturn(null);

		// Allow the default method to be executed
		doCallRealMethod().when(weatherLocationRepository).safeFindByZipCode(WeatherData.ZIPCODE_NOTFOUND);
		// When
		WeatherLocation result = weatherLocationRepository.safeFindByZipCode(WeatherData.ZIPCODE_NOTFOUND);

		assertNull(result);
		verify(weatherLocationRepository, times(1)).findByZipCode(WeatherData.ZIPCODE_NOTFOUND);
	}

	@SuppressWarnings("serial")
	@Test
	void testSafeFindByZipCode_DatabaseException() {
		// Given - Simulating DataAccessException scenario
		when(weatherLocationRepository.findByZipCode(anyString()))
				.thenThrow(new DataAccessException("Database failure") {
				});

		// Allow the default method to be executed
		doCallRealMethod().when(weatherLocationRepository).safeFindByZipCode(WeatherData.ZIPCODE);
		// When & Then
		Exception exception = assertThrows(WeatherRepositoryWeatherAppException.class,
				() -> weatherLocationRepository.safeFindByZipCode(WeatherData.ZIPCODE));

		assertThat(exception.getMessage().contains("Database failure"));
		verify(weatherLocationRepository, times(1)).findByZipCode(WeatherData.ZIPCODE);
	}
}
