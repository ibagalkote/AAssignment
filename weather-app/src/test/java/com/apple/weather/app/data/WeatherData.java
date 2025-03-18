package com.apple.weather.app.data;

import java.time.LocalDateTime;

import com.apple.weather.app.domain.WeatherLocation;
import com.apple.weather.app.dto.WeatherLocationDTO;

public class WeatherData {

	public static final String ZIPCODE = "11223";
	public static final String ZIPCODE_ANOTHER = "12345";
	public static final String CITY = "New York";
	public static final String CITY_ANOTHER = "Los Angeles";
	public static final String ZIPCODE_NOTFOUND = "99999";

	public static WeatherLocation getWeatherLocation1() {
		return WeatherLocation.builder().locationId(1L).createdAt(LocalDateTime.now()).zipCode(ZIPCODE).city(CITY)
				.build();
	}

	public static WeatherLocation getWeatherLocation2() {
		return WeatherLocation.builder().locationId(2L).createdAt(LocalDateTime.now()).zipCode(ZIPCODE_ANOTHER)
				.city(CITY_ANOTHER).build();
	}

	public static WeatherLocationDTO getWeatherLocationDTO1() {
		return WeatherLocationDTO.builder().locationId(1L).createdAt(LocalDateTime.now()).zipCode(ZIPCODE).city(CITY)
				.build();
	}

	public static WeatherLocationDTO getWeatherLocationDTO2() {
		return WeatherLocationDTO.builder().locationId(2L).createdAt(LocalDateTime.now()).zipCode(ZIPCODE_ANOTHER)
				.city(CITY_ANOTHER).build();
	}
}
