# Stackoverflow clone
_A simple Spring-Boot project_ 

[![Build Status](https://travis-ci.org/ValeryKorzhavin/stackoverflow.svg?branch=master)](https://travis-ci.org/ValeryKorzhavin/stackoverflow)
[![Maintainability](https://api.codeclimate.com/v1/badges/585b28b85a4fd5d79713/maintainability)](https://codeclimate.com/github/ValeryKorzhavin/stackoverflow/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/585b28b85a4fd5d79713/test_coverage)](https://codeclimate.com/github/ValeryKorzhavin/stackoverflow/test_coverage)

# About
This project was created for educational purposes. 

See demo: [Stackoverflow clone](https://springdemo-valerykorzh.herokuapp.com/)


# Tags 
Spring Boot, PostgreSQL, Liquibase, Lombok, Bootstrap, Heroku, JUnit

### Requirements 
- Java 12

### Technology Stack
- Java 12
- Spring Boot 2
- Spring Data JPA
- Spring Security
- Hibernate
- PostgreSQL
- Liquibase (for migrations)
- JUnit for testing
- Thymeleaf as a template engine
- Project Lombok
- Mapstruct
- Twitter Bootstrap 4
- JQuery
- Built with Maven
- Deployed on Heroku

### Compile and Run
```shell script
$ make # build & run
$ make test # compile & tests
```

### Database migrations
Liquibase is used for creating and updating database schema. Schemas are located at /src/main/resources/db/changelog.

Run this command for generating new migration:
```shell script
$ make generate-migration
```

### Features
- Create/Update Accounts/Questions/Answers
- Upload avatar
- Vote for Questions/Answers
- Sorting Questions/Accounts/Tags by votes, name, creation date
