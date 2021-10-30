package me.guillaume.recruitment.gossip.message.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultMessageStrategyFactoryTest {

    MessageStrategyFactory messageStrategyFactory = new DefaultMessageStrategyFactory();

    @Test
    void createMessageStrategyWhenMrReturnsMisterMessageStrategy() {
        assertThat(messageStrategyFactory.createMessageStrategy("Mr")).isExactlyInstanceOf(MisterMessageStrategy.class);
    }

    @Test
    void createMessageStrategyWhenDrReturnsMisterMessageStrategy() {
        assertThat(messageStrategyFactory.createMessageStrategy("Dr")).isExactlyInstanceOf(DoctorMessageStrategy.class);
    }

    @Test
    void createMessageStrategyWhenTitleUnknownThrows() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> messageStrategyFactory.createMessageStrategy("Xy"))
                .withMessage("The title Xy is not supported.");
    }

}
