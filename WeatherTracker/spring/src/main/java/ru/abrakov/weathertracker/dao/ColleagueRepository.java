package ru.abrakov.weathertracker.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.abrakov.weathertracker.entity.Colleague;
import ru.abrakov.weathertracker.entity.Weather;

import java.util.List;

public interface ColleagueRepository {

    /**
     * Find {@link Colleague} by id
     *
     * @param id - id colleague
     * @return {@link Colleague}
     */
    Colleague findById(int id);

    /**
     * Find all {@link Colleague}
     *
     * @return List<Colleague>
     */
    List<Colleague> findAll();

    /**
     * Find all {@link Colleague} order by {@link Weather} field updateStatus and lastDateUpdate asc
     *
     * @return List<Colleague>
     */
    List<Colleague> findAllOrderByWeatherUpdateStatus();

    /**
     * Find all {@link Colleague} with Pagination;
     *
     * @param pageable - param for pagination.
     * @return Page<Colleague>
     */
    Page<Colleague> findAllPageable(Pageable pageable);

    /**
     * save new {@link Colleague}
     *
     * @param colleague - object colleague
     */
    int save(Colleague colleague);

    /**
     * update info about {@link Colleague}
     *
     * @param colleague - object colleague
     */
    void update(Colleague colleague);

    /**
     * logic delete {@link Colleague}, set deleted = true
     *
     * @param id - id colleague
     */
    void delete(int id);

}
