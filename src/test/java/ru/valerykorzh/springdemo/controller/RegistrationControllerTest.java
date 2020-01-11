package ru.valerykorzh.springdemo.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static ru.valerykorzh.springdemo.controller.ControllerConstants.REGISTRATION_PATH;
import static ru.valerykorzh.springdemo.controller.ControllerConstants.TEST_HOST;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void registrationPageTest() {
        given().when()
                .get(TEST_HOST + ":" + port + REGISTRATION_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

}