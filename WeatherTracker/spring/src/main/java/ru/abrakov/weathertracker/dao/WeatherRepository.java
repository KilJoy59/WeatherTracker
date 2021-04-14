package ru.abrakov.weathertracker.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.abrakov.weathertracker.entity.Colleague;
import ru.abrakov.weathertracker.entity.ColleagueWeather;
import ru.abrakov.weathertracker.entity.Weather;

import java.util.List;

public interface WeatherRepository {

    /**
     * Save new {@link Weather}
     *
     * @param weather - object {@link Weather}
     */
    void save(Weather weather);

    /**
     * Find {@link ColleagueWeather} by id {@link Colleague}
     *
     * @param id - id colleague/
     * @return ColleagueWeather
     */
    ColleagueWeather findColleagueWeatherByColleagueId(int id);

    /**
     * Find {@link Weather} by id {@link Colleague}
     *
     * @param idColleague - id colleague/
     * @return Weather
     */
    Weather findByColleagueId(int idColleague);

    /**
     * Find all {@link ColleagueWeather}
     *
     * @return List<ColleagueWeather>
     */
    List<ColleagueWeather> findAll();

    /**
     * Find all {@link ColleagueWeather} with pagination.
     *
     * @param pageable - param for pagination.
     * @return Page<ColleagueWeather>
     */
    Page<ColleagueWeather> findAllPageable(Pageable pageable);

    /**
     * Update {@link Weather} by id {@link Colleague}
     *
     * @param weather     - object Weather.
     * @param idColleague - id colleague.
     */
    void update(Weather weather, int idColleague);
}
