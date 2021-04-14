package ru.abrakov.weathertracker.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.abrakov.weathertracker.dao.WeatherRepository;
import ru.abrakov.weathertracker.entity.colleague.ColleagueWeather;
import ru.abrakov.weathertracker.entity.weather.Weather;
import ru.abrakov.weathertracker.mapper.WeatherColleagueMapper;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
@AllArgsConstructor
public class WeatherRepositoryImpl implements WeatherRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Weather weather) {
        String query = "insert into weather(id_colleague, temperature, description, last_date_update, update_status) " +
                "values(?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, weather.getIdColleague(), weather.getTemperature(), weather.getDescription(),
                weather.getLastDateUpdate(), weather.getUpdateStatus());
    }

    @Override
    public ColleagueWeather findColleagueWeatherByColleagueId(int idColleague) {
        String query = "select c.*, w.* from weather w inner join colleague c on c.id = w.id_colleague where c.id = ?";
        return jdbcTemplate.query(query, new Object[]{idColleague}, new WeatherColleagueMapper())
                .stream().findFirst().orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Weather findByColleagueId(int idColleague) {
        String query = "select * from weather where id_colleague = ?";
        return jdbcTemplate.query(query, new Object[]{idColleague}, new BeanPropertyRowMapper<>(Weather.class))
                .stream().findAny().orElseThrow(EntityExistsException::new);
    }

    @Override
    public List<ColleagueWeather> findAll() {
        String query = "select c.*, w.* from weather w inner join colleague c on c.id = w.id_colleague";
        return jdbcTemplate.query(query, new WeatherColleagueMapper());
    }

    @Override
    public Page<ColleagueWeather> findAllPageable(Pageable pageable) {
        Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by("id");
        String query = "select c.*, w.* from weather w inner join colleague c on c.id = w.id_colleague order by c." + order.getProperty() + " " + order.getDirection().name() +
                " limit " + pageable.getPageSize() + " offset " + pageable.getOffset();
        List<ColleagueWeather> colleagueWeathers = jdbcTemplate.query(query, new WeatherColleagueMapper());
        return new PageImpl<>(colleagueWeathers, pageable, count());
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from weather w inner join colleague c on c.id = w.id_colleague", Integer.class);
    }

    @Override
    public void update(Weather weather, int idColleague) {
        jdbcTemplate.update("update weather set temperature = ?, description = ?, last_date_update = ?, update_status = ? where id_colleague = ?",
                weather.getTemperature(), weather.getDescription(), weather.getLastDateUpdate(), weather.getUpdateStatus(), idColleague);
    }
}
