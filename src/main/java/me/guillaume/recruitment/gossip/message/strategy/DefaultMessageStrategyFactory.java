package me.guillaume.recruitment.gossip.message.strategy;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

public class DefaultMessageStrategyFactory implements MessageStrategyFactory {
    private final Map<String, Supplier<MessageStrategy>> titleToMessageStrategy =
            ImmutableMap.<String, Supplier<MessageStrategy>>builder()
                .put("Mr", MisterMessageStrategy::new)
                .put("Dr", DoctorMessageStrategy::new)
                .put("Agent", AgentMessageStrategy::new)
                .put("Pr", ProfessorMessageStrategy::new)
                .put("Lady", MisterMessageStrategy::new)
                .build();

    @Override
    public MessageStrategy createMessageStrategy(String title) {
        checkArgument(titleToMessageStrategy.containsKey(title), "The title %s is not supported.", title);
        return titleToMessageStrategy.get(title).get();
    }

}
