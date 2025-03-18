package com.apple.weather.app.adapters.secondary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apple.weather.app.domain.WeatherLocation;
import com.apple.weather.app.exceptions.WeatherRepositoryWeatherAppException;

@Repository
public interface WeatherLocationRepository extends JpaRepository<WeatherLocation, Long> {
	static final Logger logger = LoggerFactory.getLogger(WeatherLocationRepository.class);


	@Query("SELECT wl FROM WeatherLocation wl " + "LEFT JOIN FETCH wl.dailyWeather dw "
			+ "LEFT JOIN FETCH wl.extendedForecasts ef " + "WHERE wl.zipCode = :zipCode")
	@EntityGraph(attributePaths = { "dailyWeather", "extendedForecasts" })
	public WeatherLocation findByZipCode(@Param("zipCode") String zipCode);

	default WeatherLocation safeFindByZipCode(String zipCode) {
        logger.debug("Querying database for zip code: {}", zipCode);
        
		try {
			return findByZipCode(zipCode);
		} catch (DataAccessException ex) {
            logger.error("Database error while fetching weather data for zip code: {}", zipCode, ex);
			throw new WeatherRepositoryWeatherAppException("Database error while fetching weather data", ex);
		}
	}
}
