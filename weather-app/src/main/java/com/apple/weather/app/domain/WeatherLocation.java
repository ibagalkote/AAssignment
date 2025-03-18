package com.apple.weather.app.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "weather_location")
public class WeatherLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long locationId;
    
    public String addressLine1;
    public String addressLine2;
    public String city;
    public String state;
    public String country;
    public String zipCode;
    public Double latitude;
    public Double longitude;
    public LocalDateTime createdAt;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    @Builder.Default
    public Set<DailyWeather> dailyWeather = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    @Builder.Default
    public Set<ExtendedForecast> extendedForecasts = new HashSet<>();
}