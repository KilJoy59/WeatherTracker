package ru.abrakov.weathertracker.entity.colleague;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colleague {

    private int id;

    private String surname;

    private String name;

    private String patronymic;

    private String position;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String city;

    private boolean deleted;

    @JsonIgnore
    private LocalDateTime createDate;

    @JsonIgnore
    private LocalDateTime deleteDate;

    public Colleague(int id, String surname, String name, String patronymic, String position, LocalDate birthDate, String city, boolean deleted) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.position = position;
        this.birthDate = birthDate;
        this.city = city;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Colleague{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
