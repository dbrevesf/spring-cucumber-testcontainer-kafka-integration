package com.example.demo.testcontainer.config;

import com.example.demo.kafka.config.KafkaContainerSetup;
import com.example.demo.mongo.config.MongoContainerSetup;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;


@Import({KafkaContainerSetup.class,
        MongoContainerSetup.class})
@DirtiesContext
@Testcontainers
public class TestContainerSetup {
}
