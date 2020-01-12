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

### Core Technologies
- Java 12
- Spring Boot 2
- PostgreSQL
- Liquibase (for migrations)
- JUnit for testing
- Thymeleaf as a template engine
- Lombok
- Mapstruct
- Twitter Bootstrap 4
- JQuery
- Build with Maven
- Deployed on Heroku

### Compile and Run
```aidl
$ make # build & run
$ make test # compile & tests

```

### Database migrations
Liquibase is used for creating and updating database schema. Schemas are located at /src/main/resources/db/changelog.

Run this command for generating new migration:
```aidl
$ make generate-migration
```

### Features
- Create/Update/Delete Accounts
- Upload/Delete avatar
- Sending email for account confirmation
- Vote for questions/answers
- Ajax search with autocomplete
- Sorting Questions and Answers by Rating
