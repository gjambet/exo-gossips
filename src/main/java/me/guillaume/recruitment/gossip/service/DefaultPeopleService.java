package me.guillaume.recruitment.gossip.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.google.common.annotations.VisibleForTesting;

import me.guillaume.recruitment.gossip.domain.Person;
import me.guillaume.recruitment.gossip.message.strategy.DefaultMessageStrategyFactory;
import me.guillaume.recruitment.gossip.message.strategy.MessageStrategy;
import me.guillaume.recruitment.gossip.message.strategy.MessageStrategyFactory;

public class DefaultPeopleService implements PeopleService {

    private static final String MISTER_TYPE_AND_NAME_SEPARATOR = " ";

    private final LinkedHashMap<String, Person> people = new LinkedHashMap<>();
    private final MessageStrategyFactory messageStrategyFactory;

    public DefaultPeopleService() {
        this.messageStrategyFactory = new DefaultMessageStrategyFactory();
    }

    @VisibleForTesting
    DefaultPeopleService(MessageStrategyFactory messageStrategyFactory) {
        this.messageStrategyFactory  = messageStrategyFactory;
    }

    @Override
    public void createAndSavePerson(String misterString) {
        Person person = createPerson(misterString);
        savePerson(person);
    }

    private Person createPerson(String misterString) {
        String[] misterTypeAndNameArray = misterString.split(MISTER_TYPE_AND_NAME_SEPARATOR);
        String title = misterTypeAndNameArray[0];
        String name = misterTypeAndNameArray[1];
        MessageStrategy messageStrategy = messageStrategyFactory.createMessageStrategy(title);
        return new Person(name, messageStrategy);
    }

    private void savePerson(Person person) {
        people.put(person.getName(), person);
    }

    @Override
    public Person findPersonOrThrow(String personName) {
        Person person = people.get(personName);
        checkArgument(person != null, "The person %s passed as argument is not known. People known are: %s",
                personName, people.keySet());
        return person;
    }

    @Override
    public Collection<Person> findAll() {
        return people.values();
    }

    @VisibleForTesting
    LinkedHashMap<String, Person> getPeople() {
        return people;
    }
}
