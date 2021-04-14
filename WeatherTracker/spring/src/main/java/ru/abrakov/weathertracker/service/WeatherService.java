package ru.abrakov.weathertracker.service;

import org.springframework.data.domain.Page;
import ru.abrakov.weathertracker.entity.ColleagueWeather;
import ru.abrakov.weathertracker.entity.Weather;
import ru.abrakov.weathertracker.entity.WeatherCity;
import ru.abrakov.weathertracker.entity.WeatherUrl;

import java.util.List;
import java.util.Map;

public interface WeatherService {

    /**
     * Get {@link Weather} by id colleague
     *
     * @param idColleague - id colleague
     * @return ColleagueWeather
     */
    ColleagueWeather getWeatherByColleagueId(int idColleague);

    /**
     * Get all colleagues weather {@link ColleagueWeather}
     *
     * @return List<ColleagueWeather>
     */
    List<ColleagueWeather> getAllColleagueWeather();

    /**
     * Get all {@link ColleagueWeather} with pagination
     *
     * @param page       - number page
     * @param size       - page size
     * @param sortColumn - sorting column
     * @param direction  - sorting direction
     * @return Page<ColleagueWeather>
     */
    Page<ColleagueWeather> getAllColleagueWeatherPageable(int page, int size, String sortColumn, String direction);

    /**
     * Save {@link Weather} for colleague
     *
     * @param weatherUrl  - object {@link WeatherUrl}
     * @param idColleague - id colleague
     */
    void saveWeatherForColleague(WeatherUrl weatherUrl, int idColleague);

    /**
     * Update {@link Weather} for colleague
     *
     * @param weather     - object Weather
     * @param idColleague - id colleague
     */
    void updateWeatherForColleague(Weather weather, int idColleague);

    /**
     * Update {@link Weather} for colleague when change city
     *
     * @param weatherUrl  - object {@link WeatherUrl}
     * @param idColleague - id colleague
     */
    void updateWeatherForColleagueAfterChangeCity(WeatherUrl weatherUrl, int idColleague);

    /**
     * get {@link WeatherCity} by city
     *
     * @param city - name city
     * @return WeatherCity
     */
    WeatherCity getWeatherByCity(String city);

    /**
     * Get weather params by city name
     *
     * @param weatherUrl - object {@link WeatherUrl}
     * @return Map<String, String>
     */
    Map<String, String> getWeatherFromExternalService(WeatherUrl weatherUrl);
}
