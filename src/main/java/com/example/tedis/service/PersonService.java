package com.example.tedis.service;

import com.example.tedis.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAll();

    Person getById(String id);

    Person add(Person person);

    boolean remove(String id);
}
