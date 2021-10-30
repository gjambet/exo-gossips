package me.guillaume.recruitment.gossip.message.strategy;

import java.util.ArrayList;
import java.util.List;

import me.guillaume.recruitment.gossip.domain.Person;

class DoctorMessageStrategy implements MessageStrategy {

    private final List<String> messages = new ArrayList<>();
    private  String newMessage;
    private int itemToGossip = 0;

    @Override
    public void initializeMessage(String message) {
        messages.add(message);
    }

    @Override
    public String getMessage() {
        return MessageStrategies.formatMessages(messages);
    }

    @Override
    public boolean updateMessage(String newMessage) {
        if (this.newMessage != null) {
            return false;
        }
        this.newMessage = newMessage;
        return true;
    }

    @Override
    public void spread(Person listener) {
        if (itemToGossip < messages.size()) {
            boolean listenerUpdated = listener.updateMessage(messages.get(itemToGossip));
            if (listenerUpdated) {
                itemToGossip++;
            }
        }
    }

    @Override
    public void completeSpread() {
        if (newMessage != null) {
            messages.add(newMessage);
            newMessage = null;
        }
    }

}
