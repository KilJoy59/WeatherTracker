package ru.abrakov.weathertracker.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.abrakov.weathertracker.dao.ColleagueRepository;
import ru.abrakov.weathertracker.entity.Colleague;
import ru.abrakov.weathertracker.entity.WeatherUrl;
import ru.abrakov.weathertracker.service.ColleagueService;
import ru.abrakov.weathertracker.service.WeatherService;

import java.util.List;

@Service
@AllArgsConstructor
public class ColleagueServiceImpl implements ColleagueService {

    private final ColleagueRepository colleagueRepository;
    private final WeatherService weatherService;

    @Override
    public Colleague getColleagueById(int id) {
        return colleagueRepository.findById(id);
    }

    @Override
    public List<Colleague> getListOfAllColleagues() {
        return colleagueRepository.findAll();
    }

    @Override
    public List<Colleague> getAllColleaguesOrderByUpdateStatus() {
        return colleagueRepository.findAllOrderByWeatherUpdateStatus();
    }

    @Override
    public Page<Colleague> getPageOfAllColleagues(int page, int size, String sortColumn, String direction) {
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
        return colleagueRepository.findAllPageable(pageRequest);
    }

    @Override
    public void saveNewColleague(Colleague colleague) {
        String a = "0";
        WeatherUrl weather = new WeatherUrl(colleague.getCity());
        int idColleague = colleagueRepository.save(colleague);
        weatherService.saveWeatherForColleague(weather, idColleague);
    }

    @Override
    public void updateColleague(Colleague colleague) {
        colleagueRepository.update(colleague);
        WeatherUrl weatherUrl = new WeatherUrl(colleague.getCity());
        weatherService.updateWeatherForColleagueAfterChangeCity(weatherUrl, colleague.getId());
    }

    @Override
    public void deleteColleague(int id) {
        colleagueRepository.delete(id);
    }
}
