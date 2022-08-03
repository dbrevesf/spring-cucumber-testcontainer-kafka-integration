package com.example.demo.bdd;

import com.example.demo.kafka.KafkaConsumerForTest;
import com.example.demo.kafka.KafkaProducerForTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.containsString;

public class StepsDefinition {

    @Autowired
    private KafkaConsumerForTest consumer;

    @Autowired
    private KafkaProducerForTest producer;

    @Value("${test.topic}")
    private String topic;

    private boolean messageReceived = false;
    private final String data = "The Integration is Working Fine!";

    @Given("there is a message produced by the Kafka Producer")
    public void thereIsAMessageProducedByTheKafkaProducer() {
        producer.send(topic, data);
    }

    @When("we fetch for new message in the Kafka Consumer")
    public void weFetchForNewMessageInTheKafkaConsumer() throws InterruptedException {
        messageReceived = consumer.getLatch().await(10, TimeUnit.SECONDS);
    }

    @Then("the message should be retrieved.")
    public void theMessageShouldBeRetrieved() {
        assertTrue(messageReceived);
        assertThat(consumer.getPayload(), containsString(data));
    }


}
