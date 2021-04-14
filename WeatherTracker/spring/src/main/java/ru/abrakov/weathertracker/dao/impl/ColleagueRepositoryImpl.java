package ru.abrakov.weathertracker.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.abrakov.weathertracker.dao.ColleagueRepository;
import ru.abrakov.weathertracker.entity.colleague.Colleague;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class ColleagueRepositoryImpl implements ColleagueRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Colleague> findAll() {
        return jdbcTemplate.query("select * from colleague where deleted = false order by surname, name, patronymic asc", new BeanPropertyRowMapper<>(Colleague.class));
    }

    @Override
    public List<Colleague> findAllOrderByWeatherUpdateStatus() {
        return jdbcTemplate.query("select c.* from colleague c inner join weather w on w.id_colleague = c.id where c.deleted = false order by w.update_status, w.last_date_update asc", new BeanPropertyRowMapper<>(Colleague.class));
    }

    @Override
    public Page<Colleague> findAllPageable(Pageable pageable) {
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("id");
        String query = "select * from colleague order by " + order.getProperty() + " " + order.getDirection().name() +
                " limit " + pageable.getPageSize() + " offset " + pageable.getOffset();
        List<Colleague> colleagues = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Colleague.class));
        return new PageImpl<>(colleagues, pageable, count());
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from colleague", Integer.class);
    }

    @Override
    public Colleague findById(int id) {
        return jdbcTemplate.query("select * from colleague where id = ? and deleted = false", new Object[]{id}, new BeanPropertyRowMapper<>(Colleague.class))
                .stream().findAny().orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public int save(Colleague colleague) {
        Date birthDate = colleague.getBirthDate() == null ? null : Date.valueOf(colleague.getBirthDate());
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("colleague").usingGeneratedKeyColumns("id");
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("surname", colleague.getSurname())
                .addValue("name", colleague.getName())
                .addValue("patronymic", colleague.getPatronymic())
                .addValue("position", colleague.getPosition())
                .addValue("birth_date", birthDate)
                .addValue("city", colleague.getCity())
                .addValue("deleted", false)
                .addValue("create_date", Date.valueOf(LocalDate.now()));
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public void update(Colleague colleague) {
        jdbcTemplate.update("update colleague set surname = ?, name = ?, patronymic = ?, position = ?, city = ?, birth_date = ? where id = ?",
                colleague.getSurname(), colleague.getName(), colleague.getPatronymic(), colleague.getPosition(), colleague.getCity(), colleague.getBirthDate(), colleague.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("update colleague set deleted = true, delete_date = current_timestamp where id = ?", id);
    }
}
