Feature: Send message to a Kafka topic and retrieve it.
  Verifies if the message was received.

  Scenario: New String Sent to Kafka Topic
    Given there is a message produced by the Kafka Producer
    When we fetch for new message in the Kafka Consumer
    Then the message should be retrieved.