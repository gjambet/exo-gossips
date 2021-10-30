package me.guillaume.recruitment.gossip;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

class AdditionalGossipsTest {
    private static final String TEST_MESSAGE = "Test message";
    private static final String UNKNOWN_PERSON = "Unknown";
    private static final String KNOWN_PERSON = "White";

    @Test
    void askThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.ask(UNKNOWN_PERSON));
    }

    @Test
    void sayToThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.say(TEST_MESSAGE).to(UNKNOWN_PERSON));
    }

    @Test
    void sayThrowsIllegalArgumentExceptionIfMessageIsNull() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.say(null))
                .withMessage("The message provided (null) is not valid");
    }
    @Test
    void sayThrowsIllegalArgumentExceptionIfMessageIsBlank() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.say(" "))
                .withMessage("The message provided ( ) is not valid");
    }

    @Test
    void fromThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.from(UNKNOWN_PERSON));
    }

    @Test
    void fromToThrowsIllegalArgumentExceptionIfMisterIsUnknown() {
        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue");

        assertThatIllegalArgumentException().isThrownBy(() -> gossips.from(KNOWN_PERSON).to(UNKNOWN_PERSON));
    }

}
