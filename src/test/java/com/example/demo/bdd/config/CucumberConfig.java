package com.example.demo.bdd.config;


import com.example.demo.testcontainers.TestContainerSetup;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@CucumberContextConfiguration
@SpringBootTest
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features")
public class CucumberConfig extends TestContainerSetup {
}
