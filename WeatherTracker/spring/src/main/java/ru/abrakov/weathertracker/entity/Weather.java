package ru.abrakov.weathertracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abrakov.weathertracker.util.Updatable;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather implements Updatable {

    private int id;

    private int idColleague;

    private String temperature;

    private String description;

    private LocalDateTime lastDateUpdate;

    private String updateStatus;

    public Weather(int idColleague, String temperature, String description, LocalDateTime lastDateUpdate, String updateStatus) {
        this.idColleague = idColleague;
        this.temperature = temperature;
        this.description = description;
        this.lastDateUpdate = lastDateUpdate;
        this.updateStatus = updateStatus;
    }

    public void updateData(Weather newWeather) {
        setIfNotNull(this::setTemperature, newWeather.getTemperature());
        setIfNotNull(this::setDescription, newWeather.getDescription());
        setIfNotNull(this::setUpdateStatus, newWeather.getUpdateStatus());
        setIfNotNull(this::setLastDateUpdate, LocalDateTime.now());
    }
}
