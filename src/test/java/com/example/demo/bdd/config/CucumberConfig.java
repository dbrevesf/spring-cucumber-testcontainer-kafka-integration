package com.example.demo.bdd.config;


import com.example.demo.testcontainer.config.TestContainerSetup;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@CucumberContextConfiguration
public class CucumberConfig extends TestContainerSetup {
}