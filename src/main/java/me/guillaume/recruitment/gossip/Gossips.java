package me.guillaume.recruitment.gossip;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.Collection;

import me.guillaume.recruitment.gossip.domain.Person;
import me.guillaume.recruitment.gossip.service.DefaultPeopleService;
import me.guillaume.recruitment.gossip.service.PeopleService;

/**
 * This class provide several functionalities to exchange gossips between misters.
 *
 * Warning: the implementation in general is not thread-safe.
 *
 * Remarks:
 * Ideally we should inject all dependencies of all classes using a DI framework, such as Spring
 * All classes must be unit tested in order for the code to be production ready. In this assignment few classes are not
 * properly unit tested (mainly implementations of MessageStrategy and Gossips)
 *
 */
public class Gossips {
    private static final String DEFAULT_MESSAGE = "";
    private final PeopleService peopleService;

    public Gossips(String... misterStrings) {
        peopleService = new DefaultPeopleService();
        Arrays.stream(misterStrings)
                .forEach(peopleService::createAndSavePerson);
    }

    public String ask(String misterName) {
        String message = peopleService.findPersonOrThrow(misterName).getMessage();
        return message == null ? DEFAULT_MESSAGE : message;
    }

    public MisterToListenerBuilder from(String misterName) {
        Person person = peopleService.findPersonOrThrow(misterName);
        return new MisterToListenerBuilder(this, person);
    }

    public MessageToEmitBuilder say(String message) {
        checkArgument(message != null && !message.isBlank(), "The message provided (%s) is not valid", message);
        return new MessageToEmitBuilder(this, message);
    }

    public void spread() {
        Collection<Person> people = peopleService.findAll();
        people.forEach(Person::spread);
        people.forEach(Person::completeSpread);
    }

    private PeopleService getPeopleService() {
        return peopleService;
    }

    static class MisterToListenerBuilder {
        private final Gossips wrappedGossip;
        private final Person person;

        MisterToListenerBuilder(Gossips gossip, Person person) {
            this.wrappedGossip = gossip;
            this.person = person;
        }

        public Gossips to(String listenerName) {
            Person listener = wrappedGossip.getPeopleService().findPersonOrThrow(listenerName);
            person.setListener(listener);
            return wrappedGossip;
        }
    }

    static class MessageToEmitBuilder {
        private final Gossips wrappedGossip;
        private final String message;

        public MessageToEmitBuilder(Gossips gossip, String message) {
            this.wrappedGossip = gossip;
            this.message = message;
        }

        public Gossips to(String misterName) {
            Person person = wrappedGossip.getPeopleService().findPersonOrThrow(misterName);
            person.initializeMessage(message);
            return wrappedGossip;
        }
    }

}
