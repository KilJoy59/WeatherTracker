package ru.abrakov.weathertracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.abrakov.weathertracker.entity.colleague.ColleagueWeather;
import ru.abrakov.weathertracker.entity.weather.WeatherCity;
import ru.abrakov.weathertracker.service.WeatherService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/colleague/{id}")
    public ColleagueWeather getColleagueWeather(@PathVariable("id") int idColleague) {
        return weatherService.getWeatherByColleagueId(idColleague);
    }

    @GetMapping("/colleague/all")
    public List<ColleagueWeather> getAllColleagueWeather() {
        return weatherService.getAllColleagueWeather();
    }

    @GetMapping("/colleague/all/page")
    public Page<ColleagueWeather> getAllColleagueWeatherPageable(@RequestParam int page, @RequestParam int size,
                                                                 @RequestParam(required = false) String sortColumn,
                                                                 @RequestParam(required = false) String direction) {
        return weatherService.getAllColleagueWeatherPageable(page, size, sortColumn, direction);
    }

    @GetMapping("/{city}")
    public WeatherCity getWeatherByCity(@PathVariable("city") String city) {
        return weatherService.getWeatherByCity(city);
    }

}
