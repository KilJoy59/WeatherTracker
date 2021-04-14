package ru.abrakov.weathertracker.schedulers;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.abrakov.weathertracker.entity.colleague.Colleague;
import ru.abrakov.weathertracker.entity.weather.Weather;
import ru.abrakov.weathertracker.entity.weather.WeatherUrl;
import ru.abrakov.weathertracker.entity.enums.WeatherParam;
import ru.abrakov.weathertracker.service.ColleagueService;
import ru.abrakov.weathertracker.service.WeatherService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class WeatherUpdater {

    private final WeatherService weatherService;
    private final ColleagueService colleagueService;

    @Transactional
    @Scheduled(fixedDelay = 30 * 1000, initialDelay = 5 * 1000)
    private void updateWeatherInfoToAllColleagues() {
        List<Colleague> colleagues = colleagueService.getAllColleaguesOrderByUpdateStatus();
        colleagues.forEach(colleague -> {
            WeatherUrl weatherUrl = new WeatherUrl(colleague.getCity());
            Map<String, String> weathetParams = weatherService.getWeatherFromExternalService(weatherUrl);
            String temperature = weathetParams.get(WeatherParam.TEMPERATURE.getParam());
            String description = weathetParams.get(WeatherParam.DESCRIPTION.getParam());
            String status = weathetParams.get(WeatherParam.STATUS.getParam());
            Weather weather = new Weather(colleague.getId(), temperature, description, LocalDateTime.now(), status);
            weatherService.updateWeatherForColleague(weather, colleague.getId());
        });
    }
}
