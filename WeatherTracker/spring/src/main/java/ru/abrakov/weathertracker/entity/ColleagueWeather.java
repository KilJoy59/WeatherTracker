package ru.abrakov.weathertracker.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColleagueWeather {

    private String surname;

    private String name;

    private String patronymic;

    private String position;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String city;

    private boolean deleted;

    private Weather weather;

    public ColleagueWeather(String surname, String name, String patronymic, String position, LocalDate birthDate, String city, boolean deleted,
                            int idColleague, String temperature, String description, LocalDateTime lastDateUpdate, String updateStatus) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.position = position;
        this.birthDate = birthDate;
        this.city = city;
        this.deleted = deleted;
        this.weather = new Weather(idColleague, temperature, description, lastDateUpdate, updateStatus);
    }
}
