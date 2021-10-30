package me.guillaume.recruitment.gossip.message.strategy;

import me.guillaume.recruitment.gossip.domain.Person;

public interface MessageStrategy {
    void initializeMessage(String message);

    String getMessage();

    /**
     *
     *
     * @param sender
     * @param newMessage candidate message to be set
     * @return true in case the new message was taken into account / false otherwise
     */
    boolean updateMessage(Person sender, String newMessage);

    void spread(Person spreader, Person listener);

    /**
     * CompleteSpread denotes the completion of a spread
     */
    void completeSpread();
}
