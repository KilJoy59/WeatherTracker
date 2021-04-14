package ru.abrakov.weathertracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.abrakov.weathertracker.entity.colleague.Colleague;
import ru.abrakov.weathertracker.service.ColleagueService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/colleague")
public class ColleagueController {

    private final ColleagueService colleagueService;

    @GetMapping("/{id}")
    public Colleague getColleagueById(@PathVariable("id") int id) {
        return colleagueService.getColleagueById(id);
    }

    @GetMapping("/all")
    public List<Colleague> getAllColleague() {
        return colleagueService.getListOfAllColleagues();
    }

    @GetMapping("/all/page")
    public Page<Colleague> getAllColleaguePageable(@RequestParam int page, @RequestParam int size,
                                                   @RequestParam(required = false) String sortColumn,
                                                   @RequestParam(required = false) String direction) {
        return colleagueService.getPageOfAllColleagues(page, size, sortColumn, direction);
    }

    @PostMapping("/new")
    public void createNewColleague(@RequestBody Colleague colleague) {
        colleagueService.saveNewColleague(colleague);
    }

    @PatchMapping("/edit")
    public void updateColleague(@RequestBody Colleague colleague) {
        colleagueService.updateColleague(colleague);
    }

    @DeleteMapping("/{id}")
    public void deleteCollegue(@PathVariable("id") int id) {
        colleagueService.deleteColleague(id);
    }
}
