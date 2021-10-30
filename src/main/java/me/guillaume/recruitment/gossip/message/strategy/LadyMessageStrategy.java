package me.guillaume.recruitment.gossip.message.strategy;

import me.guillaume.recruitment.gossip.domain.Person;

class LadyMessageStrategy implements MessageStrategy {

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
        if ("Mr".equals(sender.getTitle())) {
            return;
        }
        if ("Dr".equals(sender.getTitle())) {
            boolean listenerUpdated = listener.updateMessage(spreader, message);
            if (listenerUpdated) {
                message = null;
                this.sender = null;
            }
            return;
        }
        throw new IllegalStateException("Lady only knows how to deal with Mr or Dr, not " + this.sender.getTitle());
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
