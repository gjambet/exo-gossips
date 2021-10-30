package me.guillaume.recruitment.gossip.message.strategy;

import java.util.List;

class MessageStrategies {

    private MessageStrategies() {
        throw new IllegalStateException("This utility class must not be instanciated.");
    }

    static String formatMessages(List<String> messages) {
        return String.join(", ", messages);
    }

}
