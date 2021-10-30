package me.guillaume.recruitment.gossip.service;

import java.util.Collection;

import me.guillaume.recruitment.gossip.domain.Person;

public interface PeopleService {
    void createAndSavePerson(String misterString);

    Person findPersonOrThrow(String personName);

    Collection<Person> findAll();
}
