package me.guillaume.recruitment.gossip.message.strategy;

import java.util.ArrayList;
import java.util.List;

import me.guillaume.recruitment.gossip.domain.Person;

class AgentMessageStrategy implements MessageStrategy {
    private List<String> messages = new ArrayList<>();
    private List<String> newMessages = new ArrayList<>();

    @Override
    public void initializeMessage(String message) {
        messages.add(message);
    }

    @Override
    public String getMessage() {
        return MessageStrategies.formatMessages(messages);
    }

    @Override
    public boolean updateMessage(Person sender, String newMessage) {
        newMessages.add(newMessage);
        return true;
    }

    @Override
    public void spread(Person spreader, Person listener) {
        //
    }

    @Override
    public void completeSpread() {
        messages = newMessages;
        newMessages = new ArrayList<>();
    }
}
