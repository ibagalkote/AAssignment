package com.apple.weather.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extended_forecast")
public class ExtendedForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
