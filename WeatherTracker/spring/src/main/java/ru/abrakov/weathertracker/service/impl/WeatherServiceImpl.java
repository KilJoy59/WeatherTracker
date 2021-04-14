package ru.abrakov.weathertracker.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.abrakov.weathertracker.dao.WeatherRepository;
import ru.abrakov.weathertracker.entity.colleague.ColleagueWeather;
import ru.abrakov.weathertracker.entity.weather.Weather;
import ru.abrakov.weathertracker.entity.weather.WeatherCity;
import ru.abrakov.weathertracker.entity.weather.WeatherUrl;
import ru.abrakov.weathertracker.entity.enums.UpdateStatus;
import ru.abrakov.weathertracker.entity.enums.WeatherParam;
import ru.abrakov.weathertracker.service.WeatherService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;

    @Override
    public ColleagueWeather getWeatherByColleagueId(int idColleague) {
        return weatherRepository.findColleagueWeatherByColleagueId(idColleague);
    }

    @Override
    public List<ColleagueWeather> getAllColleagueWeather() {
        return weatherRepository.findAll();
    }

    @Override
    public Page<ColleagueWeather> getAllColleagueWeatherPageable(int page, int size, String sortColumn, String direction) {
        PageRequest pageRequest;
        if (sortColumn != null || direction != null) {
            try {
                pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortColumn);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                pageRequest = PageRequest.of(page, size);
            }
        } else {
            pageRequest = PageRequest.of(page, size);
        }
        return weatherRepository.findAllPageable(pageRequest);
    }

    @Override
    public void saveWeatherForColleague(WeatherUrl weatherUrl, int idColleague) {
        Map<String, String> weathetParams = getWeatherFromExternalService(weatherUrl);
        String temperature = weathetParams.get(WeatherParam.TEMPERATURE.getParam());
        String description = weathetParams.get(WeatherParam.DESCRIPTION.getParam());
        String status = weathetParams.get(WeatherParam.STATUS.getParam());
        Weather weather = new Weather(idColleague, temperature, description, LocalDateTime.now(), status);
        weatherRepository.save(weather);
    }

    @Override
    public void updateWeatherForColleague(Weather newWeather, int idColleague) {
        Weather oldWeather = weatherRepository.findByColleagueId(idColleague);
        oldWeather.updateData(newWeather);
        weatherRepository.update(oldWeather, idColleague);
    }

    public void updateWeatherForColleagueAfterChangeCity(WeatherUrl weatherUrl, int idColleague) {
        Map<String, String> weathetParams = getWeatherFromExternalService(weatherUrl);
        String temperature = weathetParams.get(WeatherParam.TEMPERATURE.getParam());
        String description = weathetParams.get(WeatherParam.DESCRIPTION.getParam());
        String status = weathetParams.get(WeatherParam.STATUS.getParam());
        Weather weather = new Weather(idColleague, temperature, description, LocalDateTime.now(), status);
        weatherRepository.update(weather, idColleague);
    }

    @Override
    public WeatherCity getWeatherByCity(String city) {
        WeatherUrl weatherUrl = new WeatherUrl(city);
        Map<String, String> weathetParams = getWeatherFromExternalService(weatherUrl);
        String temperature = weathetParams.get(WeatherParam.TEMPERATURE.getParam());
        String description = weathetParams.get(WeatherParam.DESCRIPTION.getParam());
        String feelsLike = weathetParams.get(WeatherParam.FEELS_LIKE.getParam());
        String wind = weathetParams.get(WeatherParam.WIND.getParam());
        WeatherCity weatherCity = new WeatherCity(city, temperature, feelsLike, description, wind);
        return weatherCity;
    }

    public Map<String, String> getWeatherFromExternalService(WeatherUrl weatherUrl) {
        Map<String, String> params = new HashMap<>();
        params.put(WeatherParam.TEMPERATURE.getParam(), "Нет данных");
        params.put(WeatherParam.DESCRIPTION.getParam(), "Нет данных");
        params.put(WeatherParam.STATUS.getParam(), UpdateStatus.ERROR.getStatus());
        params.put(WeatherParam.FEELS_LIKE.getParam(), "Нет данных");
        params.put(WeatherParam.WIND.getParam(), "Нет данных");
        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseEntity<String> response = restTemplate.getForEntity(weatherUrl.getUrl(), String.class);

            String temperature = objectMapper.readTree(Objects.requireNonNull(response.getBody())).get("main").get("temp").asText();
            params.put(WeatherParam.TEMPERATURE.getParam(), temperature);

            String description = objectMapper.readTree(response.getBody()).get("weather").get(0).get("description").asText();
            params.put(WeatherParam.DESCRIPTION.getParam(), description);

            String status = UpdateStatus.OK.getStatus();
            params.put(WeatherParam.STATUS.getParam(), status);

            String feelsLike = objectMapper.readTree(response.getBody()).get("main").get("feels_like").asText();
            params.put(WeatherParam.FEELS_LIKE.getParam(), feelsLike);

            String wind = objectMapper.readTree(response.getBody()).get("wind").get("speed").asText();
            params.put(WeatherParam.WIND.getParam(), wind);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return params;
    }
}
