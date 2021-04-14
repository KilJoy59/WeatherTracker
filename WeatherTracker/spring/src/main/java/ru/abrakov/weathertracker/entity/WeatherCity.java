package ru.abrakov.weathertracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCity {

    private String cityName;

    private String temperature;

    private String feelsLike;

    private String description;

    private String wind;
}
