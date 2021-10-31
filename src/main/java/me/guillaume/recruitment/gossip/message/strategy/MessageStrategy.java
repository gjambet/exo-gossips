package me.guillaume.recruitment.gossip.message.strategy;

import me.guillaume.recruitment.gossip.domain.Person;

public interface MessageStrategy {

    /**
     * Sets the initial message
     * @param message the initial message
     */
    void initializeMessage(String message);

    /**
     * Get the current message
     * @return the current message
     */
    String getMessage();

    /**
     *
     * @param sender the source of the message
     * @param newMessage candidate message to be set
     * @return true in case the new message was taken into account / false otherwise
     */
    boolean updateMessage(Person sender, String newMessage);

    /**
     * Triggers the spreading of a message
     * @param spreader the source of the message
     * @param listener the listener of the message
     */
    void spread(Person spreader, Person listener);

    /**
     * Triggers the actions necessary to complete a gossip's spread
     */
    void completeSpread();
}
