package com.example.tedis.controller;

import com.example.tedis.model.Person;
import com.example.tedis.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return personService.getAll();
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable String id) {
        return personService.getById(id);
    }

    @PostMapping
    public Person add(@RequestBody Person person) {
        return personService.add(person);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return personService.remove(id);
    }
}
