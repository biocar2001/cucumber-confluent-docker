package com.arq.san.kafka.streams.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kafka.Consumer;
import kafka.Producer;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class IsItFriday {
    static String isItFriday(String today) {
        return "Friday".equals(today) ? "TGIF" : "Nope";
    }
}

public class StepDefinitions {
    private String today;
    private String actualAnswer;
    private static final String TOPIC_PRODUCER = "word-count-input";

    private static final String TOPIC_SUBSCRIPTIONS = "word-count-output";

    private static final String TOPIC_CONSUMER = "word-count-output";

    private final Producer producer = new Producer();

    private final Consumer consumer = new Consumer(TOPIC_CONSUMER);

    private Map<String, Integer> snapshot = new HashMap<>();

    public StepDefinitions() throws IOException {
    }

    @Given("^that we know a number of words processed previously$")
    public void getSnapshotForCounts()
    {
        producer.emit(TOPIC_SUBSCRIPTIONS, "subscribe qwe-asd-zxc-101");
        snapshot = getConsumerRecords();
    }

    @When("^I send a new word \"([^\"]*)\"$")
    public void sendWord( String word )
    {
        producer.emit(TOPIC_PRODUCER, word);
    }

    @Then("^I should receive count for \"([^\"]*)\" word increased by (\\d+)$")
    public void iShouldReceiveCountForWordIncreasedBy( String word, int delta )
    {
        Map<String, Integer> actual = getConsumerRecords();

        Map<String, Integer> expected = new HashMap<>();
        expected.put(word, snapshot.getOrDefault(word, 0) + delta);

        Assert.assertEquals("Rows in kafka doesn't match expected", expected, actual);
    }

    private Map<String, Integer> getConsumerRecords()
    {
        return consumer.consume();
    }
    @Given("today is {string}")
    public void today_is(String today) {
        this.today = today;
    }

    @When("I ask whether it's Friday yet")
    public void i_ask_whether_it_s_Friday_yet() {
        actualAnswer = IsItFriday.isItFriday(today);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        assertEquals(expectedAnswer, actualAnswer);
    }
}
