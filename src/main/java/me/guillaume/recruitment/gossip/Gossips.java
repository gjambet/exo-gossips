package me.guillaume.recruitment.gossip;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.MoreCollectors;

/**
 * This class provide several functionalities to exchange gossips between misters.
 *
 * The implementation is not thread-safe, given the tests, it does not seem to be a requirement.
 */
public class Gossips {
    private static final String NO_MESSAGE = "";

    private final Set<String> misters;
    private final Map<String, String> misterToListener;
    private final Map<String, String> misterToMessage;

    public Gossips(String... misters) {
        this.misters = Arrays.stream(misters).collect(toSet());
        this.misterToListener = new HashMap<>();
        this.misterToMessage = new HashMap<>();
    }

    public MisterToListenerBuilder from(String mister) {
        assertMisterIsKnown(mister);
        return new MisterToListenerBuilder(this, mister);
    }

    public MessageToEmitBuilder say(String message) {
        checkArgument(message != null && !message.isBlank(), "The message provided (%s) is not valid", message);
        return new MessageToEmitBuilder(this, message);
    }

    public String ask(String mister) {
        assertMisterIsKnown(mister);
        return misterToMessage.getOrDefault(mister, NO_MESSAGE);
    }

    public void spread() {
        Map<String, String> newMisterToMessage = new HashMap<>();
        for (String mister : misterToListener.keySet()) {
            String listener = misterToListener.get(mister);
            if (misterToMessage.containsKey(mister)) {
                newMisterToMessage.put(listener, misterToMessage.get(mister));
            }
        }
        replaceContent(misterToMessage, newMisterToMessage);
    }


    private static void replaceContent(Map<String, String> source, Map<String, String> newContent) {
        source.clear();
        source.putAll(newContent);
    }

    private Map<String, String> getMisterToListener() {
        return misterToListener;
    }

    private Map<String, String> getMisterToMessage() {
        return misterToMessage;
    }

    private void assertMisterIsKnown(String mister) {
        // TODO: this check is not ultra-robust, it would for instance accepts a "mister" whose name is "Mr" since it will
        // match the first part of an existing mister. To be improved in subsequent commits.
        Optional<String> existingMister = misters.stream()
                .filter(m -> m.contains(mister))
                .collect(MoreCollectors.toOptional());
        checkArgument(existingMister.isPresent(), "The mister %s passed as argument is not known. Misters known are: %s",
                mister, misters);
    }

    static class MisterToListenerBuilder {
        private final Gossips wrappedGossip;
        private final String mister;

        MisterToListenerBuilder(Gossips gossip, String mister) {
            this.wrappedGossip = gossip;
            this.mister = mister;
        }

        public Gossips to(String listener) {
            wrappedGossip.assertMisterIsKnown(listener);
            wrappedGossip.getMisterToListener().put(mister, listener);
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

        public Gossips to(String mister) {
            wrappedGossip.assertMisterIsKnown(mister);
            wrappedGossip.getMisterToMessage().put(mister, message);
            return wrappedGossip;
        }
    }

}
