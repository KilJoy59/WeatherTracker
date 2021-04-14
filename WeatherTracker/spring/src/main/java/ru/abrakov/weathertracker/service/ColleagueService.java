package ru.abrakov.weathertracker.service;

import org.springframework.data.domain.Page;
import ru.abrakov.weathertracker.entity.Colleague;
import ru.abrakov.weathertracker.entity.Weather;

import java.util.List;

public interface ColleagueService {

    /**
     * Find {@link Colleague} by id
     *
     * @param id - id colleague
     * @return {@link Colleague}
     */
    Colleague getColleagueById(int id);

    /**
     * Get all {@link Colleague}
     *
     * @return List<Colleague>
     */
    List<Colleague> getListOfAllColleagues();

    /**
     * Get all {@link Colleague} order by {@link Weather} fields updateStatus and lastDateUpdate asc
     * @return List<Colleague>
     */
    List<Colleague> getAllColleaguesOrderByUpdateStatus();

    /**
     * Get all {@link Colleague} with pagination
     * @param page - number page
     * @param size - page size
     * @param sortColumn - sorting column
     * @param direction - sorting direction
     * @return
     */
    Page<Colleague> getPageOfAllColleagues(int page, int size, String sortColumn, String direction);

    /**
     * save new {@link Colleague}
     *
     * @param colleague - object colleague
     */
    void saveNewColleague(Colleague colleague);

    /**
     * update info about {@link Colleague}
     *
     * @param colleague - object colleague
     */
    void updateColleague(Colleague colleague);

    /**
     * logic delete {@link Colleague}, set deleted = true
     *
     * @param id - id colleague
     */
    void deleteColleague(int id);
}
