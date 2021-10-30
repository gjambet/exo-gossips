package me.guillaume.recruitment.gossip.message.strategy;

import me.guillaume.recruitment.gossip.domain.Person;

class MisterMessageStrategy implements MessageStrategy {

    private String message;
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
        isUpdated = true;
        return true;
    }

    @Override
    public void spread(Person spreader, Person listener) {
        if (message == null){
            return;
        }
        boolean listenerUpdated = listener.updateMessage(spreader, message);
        if (listenerUpdated) {
            message = null;
        }
    }

    @Override
    public void completeSpread() {
        if (isUpdated) {
            message = newMessage;
        }
        newMessage = null;
        isUpdated = false;
    }

}
