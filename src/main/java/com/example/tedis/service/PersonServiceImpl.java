package com.example.tedis.service;

import com.example.tedis.model.Person;
import com.example.tedis.repository.PersonRepository;
import io.lettuce.core.internal.LettuceLists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAll() {
        return LettuceLists.newList(personRepository.findAll());
    }

    @Override
    public Person getById(String id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person add(Person person) {
        return personRepository.save(person);
    }

    @Override
    public boolean remove(String id) {
        boolean exist = personRepository.existsById(id);
        if (exist) {
            personRepository.deleteById(id);
        }
        return exist;
    }
}
