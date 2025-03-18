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
@Table(name = "daily_weather")
public class DailyWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long weatherId;

    public LocalDate weatherDate;
    public Double currentTemperature;
    public LocalDateTime createdAt;
}
