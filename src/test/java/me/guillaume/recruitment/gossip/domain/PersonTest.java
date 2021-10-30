package me.guillaume.recruitment.gossip.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.guillaume.recruitment.gossip.message.strategy.MessageStrategy;

@ExtendWith(MockitoExtension.class)
class PersonTest {

    private static final String TEST_PERSON_TITLE = "Mr";
    private static final String TEST_PERSON_NAME = "Test";
    private static final String TEST_MESSAGE = "Message";

    @Mock
    private MessageStrategy messageStrategy;

    private Person person;
    private Person person2;

    @BeforeEach
    void setUp() {
        person = new Person(TEST_PERSON_TITLE, TEST_PERSON_NAME, messageStrategy);
        person2 = new Person(TEST_PERSON_TITLE, TEST_PERSON_NAME + 2, messageStrategy);
    }

    @Test
    void getNameReturnsCorrectName() {
        assertThat(person.getName()).isEqualTo(TEST_PERSON_NAME);
    }

    @Test
    void initializeMessageDelegatesToMessageStrategy() {
        person.initializeMessage(TEST_MESSAGE);
        verify(messageStrategy).initializeMessage(TEST_MESSAGE);
    }

    @Test
    void getMessageDelegatesToMessageStrategy() {
        when(messageStrategy.getMessage()).thenReturn(TEST_MESSAGE);
        assertThat(person.getMessage()).isEqualTo(TEST_MESSAGE);
        verify(messageStrategy).getMessage();
    }

    @Test
    void updateMessageDelegatesToMessageStrategy() {
        when(messageStrategy.updateMessage(any(), any())).thenReturn(true);
        assertThat(person.updateMessage(person2, TEST_MESSAGE)).isTrue();
        verify(messageStrategy).updateMessage(person2, TEST_MESSAGE);
    }

    @Test
    void spreadDelegatesToMessageStrategy() {
        Person listener = mock(Person.class);
        person.setListener(listener);
        person.spread();
        verify(messageStrategy).spread(person, listener);
    }

    @Test
    void spreadWithoutListenerDoesNothing() {
        person.spread();
        verifyNoInteractions(messageStrategy);
    }

    @Test
    void completeSpreadDelegatesToMessageStrategy() {
        person.completeSpread();
        verify(messageStrategy).completeSpread();
    }

}
