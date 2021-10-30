package me.guillaume.recruitment.gossip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.guillaume.recruitment.gossip.domain.Person;
import me.guillaume.recruitment.gossip.message.strategy.MessageStrategy;
import me.guillaume.recruitment.gossip.message.strategy.MessageStrategyFactory;

@ExtendWith(MockitoExtension.class)
class DefaultPeopleServiceTest {

    public static final String TEST_PERSON_TITLE = "Mr";
    public static final String TEST_PERSON_NAME = "Test";
    @Mock
    private MessageStrategyFactory messageStrategyFactory;

    @InjectMocks
    DefaultPeopleService peopleService;

    @Test
    void createAndSavePersonCorrectlyCreatesThePerson() {
        MessageStrategy messageStrategy = mock(MessageStrategy.class);
        when(messageStrategyFactory.createMessageStrategy(TEST_PERSON_TITLE)).thenReturn(messageStrategy);

        peopleService.createAndSavePerson(TEST_PERSON_TITLE + " " + TEST_PERSON_NAME);

        Person expectedPerson = buildExpectedPerson(TEST_PERSON_TITLE, TEST_PERSON_NAME, messageStrategy);
        assertThat(peopleService.getPeople().values()).contains(expectedPerson);
        verify(messageStrategyFactory).createMessageStrategy(TEST_PERSON_TITLE);
    }

    @Test
    void findAllReturnsAllPerson() {
        MessageStrategy messageStrategy = mock(MessageStrategy.class);
        Person expectedPerson1 = buildExpectedPerson(TEST_PERSON_TITLE, TEST_PERSON_NAME, messageStrategy);
        Person expectedPerson2 = buildExpectedPerson(TEST_PERSON_TITLE, TEST_PERSON_NAME + "2", messageStrategy);
        peopleService.getPeople().put(expectedPerson1.getName(), expectedPerson1);
        peopleService.getPeople().put(expectedPerson2.getName(), expectedPerson2);

        Collection<Person> allPersons = peopleService.findAll();

        assertThat(allPersons).containsExactly(expectedPerson1, expectedPerson2);
    }

    @Test
    void findPersonOrThrowIfPersonExistsReturnsIt() {
        MessageStrategy messageStrategy = mock(MessageStrategy.class);
        Person expectedPerson1 = buildExpectedPerson(TEST_PERSON_TITLE, TEST_PERSON_NAME, messageStrategy);
        peopleService.getPeople().put(expectedPerson1.getName(), expectedPerson1);

        assertThat(peopleService.findPersonOrThrow(TEST_PERSON_NAME)).isEqualTo(expectedPerson1);
    }

    @Test
    void findPersonOrThrowIfPersonDoesNotExistsThrows() {
        MessageStrategy messageStrategy = mock(MessageStrategy.class);
        Person expectedPerson2 = buildExpectedPerson(TEST_PERSON_TITLE, TEST_PERSON_NAME + "2", messageStrategy);
        peopleService.getPeople().put(expectedPerson2.getName(), expectedPerson2);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> peopleService.findPersonOrThrow(TEST_PERSON_NAME))
                .withMessage("The person Test passed as argument is not known. People known are: [Test2]");
    }

    private static Person buildExpectedPerson(String title, String name, MessageStrategy messageStrategy) {
        return new Person(title, name, messageStrategy);
    }
}
