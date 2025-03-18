package com.apple.weather.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtendedForecastDTO {

    public Long forecastId;
    public LocalDate forecastDate;
    public Double highTemperature;
    public Double lowTemperature;
    public String conditions;
    public Double precipitationChance;
    public Double windSpeed;
    public Double humidity;
    public LocalDateTime createdAt;
}
