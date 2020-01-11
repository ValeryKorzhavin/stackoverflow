package ru.valerykorzh.springdemo.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static ru.valerykorzh.springdemo.controller.ControllerConstants.LOGIN_PATH;
import static ru.valerykorzh.springdemo.controller.ControllerConstants.TEST_HOST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void loginPageTest() {
        given().when()
                .get(TEST_HOST + ":" + port + LOGIN_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

}