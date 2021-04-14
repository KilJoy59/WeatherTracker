package ru.abrakov.weathertracker.mapper;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import ru.abrakov.weathertracker.entity.colleague.Colleague;
import ru.abrakov.weathertracker.entity.colleague.ColleagueWeather;
import ru.abrakov.weathertracker.entity.weather.WeatherColleague;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherColleagueMapper implements RowMapper<ColleagueWeather> {

    @Override
    public ColleagueWeather mapRow(ResultSet resultSet, int i) throws SQLException {
        Colleague colleague = (new BeanPropertyRowMapper<>(Colleague.class)).mapRow(resultSet, i);
        WeatherColleague weather = (new BeanPropertyRowMapper<>(WeatherColleague.class)).mapRow(resultSet, i);
        ColleagueWeather colleagueWeather = new ColleagueWeather();
        colleagueWeather.setWeather(weather);
        colleagueWeather.setSurname(colleague.getSurname());
        colleagueWeather.setName(colleague.getName());
        colleagueWeather.setPatronymic(colleague.getPatronymic());
        colleagueWeather.setPosition(colleague.getPosition());
        colleagueWeather.setBirthDate(colleague.getBirthDate());
        colleagueWeather.setCity(colleague.getCity());
        colleagueWeather.setDeleted(colleague.isDeleted());
        return colleagueWeather;
    }
}
