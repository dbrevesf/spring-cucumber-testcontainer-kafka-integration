# Tests implementation using Cucumber, TestContainer, Kafka in a Spring project

## Specs:

- Apple M1 Pro 
- MacOS Monterrey
- Java 11.0.15.9.1-amzn
- JUnit 5
- SpringBoot 2.7.2
- Cucumber 7.4.1
- TestContainer 1.17.3
- Kafka 6.2.1
- Mongo 4.0.10
- Colima 0.4.4
- Docker 20.01.17



## Docker and Colima Issues

There are some issues that one can face when using Colima to work with Docker (usually MacOS users). If that's the case, the following command should be executed and the environment variables should be set. The lines below can also be put within the configuration file of the terminal (`.bashrc`, `.zshrc`, `.profile`, etc):

```
$ docker context use colima

$ export TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock
$ export DOCKER_HOST="unix://${HOME}/.colima/docker.sock"

```

It is required because when Colima is running, the `docker.sock` is not being placed at the usual directory `/var/run` so, the previous commands must be executed an then `TestContainer` runs properly.

## Tutorial

This tutorial considers that all the dependencies are allready installed (JDK, Gradle, Colima, Docker, etc)

1. Create a Spring Boot Application
    1. Create a new directory: `mkdir spring-cucumber-kafka-integration`.
    2. Enter the directory: `cd spring-cucumber-kafka-integration`.
    3. Create a new **basic** Gradle project with **Groovy** for the DSL: `gradle init`.
    4. Open the `build.gradle` file and fills it with the following code:
    
  
    ```
      plugins {
        id 'org.springframework.boot' version '2.7.2'
        id 'io.spring.dependency-management' version '1.0.12.RELEASE'
        id 'java'
      }

      group = 'com.example'

      repositories {
          mavenCentral()
      }

      dependencies {

          // Spring
          implementation 'org.springframework.boot:spring-boot-starter'
          testImplementation 'org.springframework.boot:spring-boot-starter-test'
          implementation 'org.springframework.kafka:spring-kafka:2.9.0'
          testImplementation 'org.springframework.kafka:spring-kafka-test:2.9.0'

          // TestContainer
          implementation 'org.testcontainers:kafka:1.17.3'
          testImplementation "org.testcontainers:junit-jupiter:1.17.3"
          testImplementation "org.testcontainers:mongodb:1.17.3"

          // Cucumber
          testImplementation 'io.cucumber:cucumber-java:7.4.1'
          testImplementation 'io.cucumber:cucumber-junit:7.4.1'
          testImplementation 'io.cucumber:cucumber-spring:7.4.1'
      }
    ```
    
    5. Create a main class called KafkaProducerConsumerApplication located on `src/main/java/com/example/demo/` and create a main method with the Spring initialization. See this file [here](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/main/java/com/example/demo/KafkaProducerConsumerApplication.java). 
    
    6. Now that the basic project is setup, build the project: `./gradlew build`.

2. Create a Cucumber Test

    1. Create a file called `cucumber.feature` located within the following directory: `src/test/resources/`.
    2. Write the first test scenarios:

    ```
      Feature: Send message to a Kafka topic and retrieve it.
      Verifies if the message was received.

      Scenario: New String Sent to Kafka Topic
        Given there is a message produced by the Kafka Producer
        When we fetch for new message in the Kafka Consumer
        Then the message should be retrieved.
    ```
  
3. Create TestContainers setup classes (TestContainer, Kafka and Mongo)

    1. Create a class called `KafkaContainerSetup` within the directory `src/test/java/com/example/demo/kafka/config/` with the following [content](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/test/java/com/example/demo/kafka/config/KafkaContainerSetup.java).

    2. Create a class called `MongoContainerSetup` within the directory `src/test/java/com/example/demo/mongo/config/` with the following [content](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/test/java/com/example/demo/mongo/config/MongoContainerSetup.java).

    3. Create a class called `TestContainerSetup` within the directory `src/test/java/com/example/demo/testcontainer/config/` with the following [content](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/test/java/com/example/demo/testcontainer/config/TestContainerSetup.java).

    4. Create a class called `CucumberConfig` within the directory `src/test/java/com/example/demo/bdd/config/` with the following [content](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/test/java/com/example/demo/bdd/config/CucumberConfig.java).


4. Create Kafka Consumer and Producer for test

    1. Create a class called `KafkaConsumerForTest` within the directory `src/test/java/com/example/demo/kafka/` with the following [content](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/test/java/com/example/demo/kafka/KafkaConsumerForTest.java).

    2. Create a class called `KafkaProducerForTest` within the directory `src/test/java/com/example/demo/kafka/` with the following [content](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/test/java/com/example/demo/kafka/KafkaProducerForTest.java).

5. Update Steps

    1. Create a file called `StepsDefinition` within the directory `src/test/java/com/example/demo/bdd/steps/` with the following [content](https://github.com/dbrevesf/spring-cucumber-testcontainer-kafka-integration/blob/main/src/test/java/com/example/demo/bdd/steps/StepsDefinition.java).
  
  The class StepsDefinition is where each step of the scenario created on `cucumber.feature` is implemented. For more details, see the official document. The link is located on the References section.
  
  
6. Create the Cucumber Task on Gradle

    1. Open the `gradle.build` file and put the following code below the `dependencies` section:
  
    ```
      configurations {
        cucumberRuntime {
            extendsFrom testImplementation
        }
      }

      task cucumber() {
        dependsOn assemble, testClasses
        doLast {
            javaexec {
                main = "io.cucumber.core.cli.Main"
                classpath = configurations.cucumberRuntime + sourceSets.main.output + sourceSets.test.output
                args = ['--plugin', 'pretty', '--glue', 'com.example.demo.bdd', 'src/test/resources']
            }
        }
      }
    ```
  
7. Create the `application.yml` file located within the directory `src/test/resources` and put the following configurations:

  ```
    spring:
      kafka:
        consumer:
          auto-offset-reset: earliest
          group-id: test-group-id
        producer:
          bootstrap-servers: localhost:9092
    test:
      topic: embedded-test-topic
  ```
  
8. Now, the project can be built and the tests can be executed with the following command: `./gradlew cucumber`.


## References

- https://cucumber.io/docs/cucumber/
- https://www.testcontainers.org/
- https://www.baeldung.com/spring-boot-kafka-testing
- https://datmt.com/backend/java/javaee/setup-cucumber-testcontainers-junit5-in-spring-boot-project/
