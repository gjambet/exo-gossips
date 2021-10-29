package me.guillaume.recruitment.gossip;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class AdditionalGossipsTest {
    private static final String TEST_MESSAGE = "Test message";
    private static final String UNKNOWN_MISTER = "Unknown";
    private static final String KNOWN_MISTER = "White";

    @Test
    public void askThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.ask(UNKNOWN_MISTER));
    }

    @Test
    public void sayToThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.say(TEST_MESSAGE).to(UNKNOWN_MISTER));
    }

    @Test
    public void sayThrowsIllegalArgumentExceptionIfMessageIsNull() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.say(null))
                .withMessage("The message provided (null) is not valid");
    }
    @Test
    public void sayThrowsIllegalArgumentExceptionIfMessageIsBlank() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.say(" "))
                .withMessage("The message provided ( ) is not valid");
    }

    @Test
    public void fromThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.from(UNKNOWN_MISTER));
    }

    @Test
    public void fromToThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.from(KNOWN_MISTER).to(UNKNOWN_MISTER));
    }

}
