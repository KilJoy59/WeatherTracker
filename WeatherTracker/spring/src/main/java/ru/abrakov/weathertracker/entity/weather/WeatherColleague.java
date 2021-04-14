package ru.abrakov.weathertracker.entity.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherColleague {

    private String temperature;

    private String description;

    private LocalDateTime lastDateUpdate;

    private String updateStatus;
}
