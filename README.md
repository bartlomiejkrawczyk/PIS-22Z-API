# Application supporting study for the exam - API
An application that collects and tests knowledge in the field of bachelor's
studies in the form of test scenarios (question strings) at various levels of difficulty.
In particular, the system should refer to the necessary sources of knowledge: materials
from lectures and exercises, auxiliary literature, GITs and other network resources.

# REST-API

The repository contains the code of the REST API service, thanks to which our android application can access data and users with different roles can read or modify data depending on the role.
Thanks to this API we have access to definitions, exercises and users.

# Pattern
In this project we use The Repository-Service Pattern:
![img](https://i.stack.imgur.com/BfNin.jpg)

The server is made using the Spring Framework.

Components:

    REST CONTROLLER - responsible for receiving requests and delegating support to services.

    DATA TRANSFER OBJECT (DTOs) - data model used for transfer between services - the same in the mobile application and on the server

    SERVICE - the service responsible for the logic supported by the api

    MAPPER - responsible for changing the "flat" structure of data in a relational database to standardized data that can be read in a mobile application

    JPA REPOSITORY - responsible for downloading and saving data in the database

    ENTITY - a data structure that directly corresponds to the structure found in a relational database.

# Data Transfer Model (DTOs)
DTOs and UML diagram are avalaible [here](https://github.com/bartlomiejkrawczyk/PIS-22Z-MODEL).

# Setup

Before starting application you need to specify `workspace.properties` file inside `src/main/java/resources/` directory.
The file should contain these properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db
spring.datasource.username=user
spring.datasource.password=password

spring.auth.expiration-time=TODO
spring.auth.secret=TODO
spring.auth.issuer=TODO

spring.elasticsearch.uris=http://localhost:9200
```
You can change these values accordingly.

# Build and start
The best way to build this project is to use IntelliJ - it auto-detects all build/test/run configurations.

If you don't want to use IntelliJ you can run application from terminal:
```bash
./gradlew clean build check bootRun
```

**Warning:** When building an application, you may run into a problem with dependencies not found.
In this case, you need to download jars directly from nexus or build the [data model](https://github.com/bartlomiejkrawczyk/PIS-22Z-MODEL) first and generate the jar:
```bash
./gradlew publishToMavenLocal
```

# Run tests
The best way to run tests is to use IntelliJ - it auto-detects all build/test/run configurations.

If you don't want to use IntelliJ you can run tests from terminal:
```bash
./gradlew clean build test
```

## Test coverage
You can generatate test report using:
```bash
./gradlew jacocoTestReport
```

Report will be avalaible [here](./build/reports/jacoco/test/html/index.html)

# Regenerate docs
Java docs are avalaible [here](./javadoc/index.html).
To regenerate docs type:
```bash
./gradlew javadoc
```
