package com.apple.weather.app.config;

import java.lang.reflect.Type;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apple.weather.app.domain.WeatherLocation;
import com.apple.weather.app.dto.DailyWeatherDTO;
import com.apple.weather.app.dto.ExtendedForecastDTO;
import com.apple.weather.app.dto.WeatherLocationDTO;

@Configuration
public class WeatherAppConfig {
	private static final Logger logger = LoggerFactory.getLogger(WeatherAppConfig.class);

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		try {
			// Define mapping for Weather to WeatherDTO
			modelMapper.createTypeMap(WeatherLocation.class, WeatherLocationDTO.class).addMappings(mapper -> {
				mapper.skip(WeatherLocationDTO::setExtendedForecasts);
				mapper.skip(WeatherLocationDTO::setDailyWeather);
			}).setPostConverter(context -> {
				WeatherLocation source = context.getSource();
				WeatherLocationDTO destination = context.getDestination();

				// Convert dailyWeather manually
				Type dailyWeatherSetType = new TypeToken<Set<DailyWeatherDTO>>() {
				}.getType();
				Set<DailyWeatherDTO> dailyWeatherDTOs = modelMapper.map(source.getDailyWeather(), dailyWeatherSetType);

				// Convert extendedForecasts manually
				Type extendedForecastSetType = new TypeToken<Set<ExtendedForecastDTO>>() {
				}.getType();
				Set<ExtendedForecastDTO> extendedForecastDTOs = modelMapper.map(source.getExtendedForecasts(),
						extendedForecastSetType);

				destination.setDailyWeather(dailyWeatherDTOs);
				destination.setExtendedForecasts(extendedForecastDTOs);
				return destination;
			});
		} catch (Exception e) {
			logger.error("Entity cannot be converted to DTO", e);
			throw new IllegalArgumentException("Cannot convert null entity to DTO", e);
		}

		return modelMapper;
	}
}
