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
public class DailyWeatherDTO {

    public Long weatherId;

    public LocalDate weatherDate;
    public Double currentTemperature;
    public LocalDateTime createdAt;
}
