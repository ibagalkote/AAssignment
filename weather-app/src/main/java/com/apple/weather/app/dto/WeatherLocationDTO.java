package com.apple.weather.app.dto;


import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherLocationDTO {
	
    private Long locationId;    
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;    
    private Set<DailyWeatherDTO> dailyWeather;    
    private Set<ExtendedForecastDTO> extendedForecasts;
}
