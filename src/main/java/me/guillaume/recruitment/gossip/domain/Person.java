package me.guillaume.recruitment.gossip.domain;

import com.google.common.base.Objects;

import me.guillaume.recruitment.gossip.message.strategy.MessageStrategy;

public class Person {
    private final String title;
    private final String name;
    private final MessageStrategy messageStrategy;
    private Person listener;

    public Person(String title, String name, MessageStrategy messageStrategy) {
        this.title = title;
        this.name = name;
        this.messageStrategy = messageStrategy;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public void setListener(Person listener) {
        this.listener = listener;
    }

    public String getMessage() {
        return messageStrategy.getMessage();
    }

    public void initializeMessage(String message) {
        messageStrategy.initializeMessage(message);
    }

    public boolean updateMessage(Person sender, String newMessage) {
        return messageStrategy.updateMessage(sender, newMessage);
    }

    public void spread() {
        if (listener != null) {
            messageStrategy.spread(this, listener);
        }
    }

    public void completeSpread() {
        messageStrategy.completeSpread();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return Objects.equal(title, person.title) && Objects.equal(name, person.name) && Objects.equal(messageStrategy,
                person.messageStrategy) && Objects.equal(listener, person.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, name, messageStrategy, listener);
    }
}
