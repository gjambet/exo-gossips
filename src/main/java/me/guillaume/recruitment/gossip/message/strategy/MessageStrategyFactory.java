package me.guillaume.recruitment.gossip.message.strategy;

public interface MessageStrategyFactory {
    MessageStrategy createMessageStrategy(String title);
}
