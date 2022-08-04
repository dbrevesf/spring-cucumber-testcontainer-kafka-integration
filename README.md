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

There is some issues that one could face when using Colima to work with Docker (usually MacOS users). If that's the case, the following command should be executed and the environment variables should be set (you can put the both `export...` lines within your configuration file of your terminal (`.bashrc`, `.zshrc`, `.profile`, etc):

```
$ docker context use colima

$ export TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock
$ export DOCKER_HOST="unix://${HOME}/.colima/docker.sock"

```

It is required because when we use Colima the `docker.sock` is not being placed at the usual directory `/var/run` so, we need to execute the previous commands so the `TestContainer` can be run properly.

## Tutorial

1) Create a Spring Boot Application
...
2) Create a Cucumber Test
...
3) Create TestContainers setup classes (TestContainer, Kafka and Mongo)
...
4) Update Steps
...


## References

- https://cucumber.io/docs/cucumber/
- https://www.testcontainers.org/
- https://www.baeldung.com/spring-boot-kafka-testing
- https://datmt.com/backend/java/javaee/setup-cucumber-testcontainers-junit5-in-spring-boot-project/






