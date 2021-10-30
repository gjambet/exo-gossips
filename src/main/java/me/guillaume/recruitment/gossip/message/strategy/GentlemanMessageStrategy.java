package me.guillaume.recruitment.gossip.message.strategy;

import me.guillaume.recruitment.gossip.domain.Person;

class GentlemanMessageStrategy implements MessageStrategy {

    private Person sender;
    private String message;
    private Person newSender;
    private String newMessage;
    private boolean isUpdated = false;

    @Override
    public void initializeMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean updateMessage(Person sender, String newMessage) {
        if (isUpdated) {
            return false;
        }
        this.newMessage = newMessage;
        this.newSender = sender;
        isUpdated = true;
        return true;
    }

    @Override
    public void spread(Person spreader, Person listener) {
        if (message == null) {
            return;
        }
        String revertedMessage = new StringBuilder(message).reverse().toString();
        boolean listenerUpdated = sender.updateMessage(spreader, revertedMessage);
        if (listenerUpdated) {
            message = null;
            sender = null;
        }
    }

    @Override
    public void completeSpread() {
        if (isUpdated) {
            message = newMessage;
            sender = newSender;
        }
        newMessage = null;
        newSender = null;
        isUpdated = false;
    }

}
