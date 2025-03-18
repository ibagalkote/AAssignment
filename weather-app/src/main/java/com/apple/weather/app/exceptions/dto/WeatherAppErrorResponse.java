package com.apple.weather.app.exceptions.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WeatherAppErrorResponse {

	private LocalDateTime timestamp;
	private String message;
	private Integer status;
	private String error;
}
