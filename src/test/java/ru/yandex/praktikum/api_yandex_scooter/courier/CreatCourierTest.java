package ru.yandex.praktikum.api_yandex_scooter.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.praktikum.api_yandex_scooter.model.courier.Courier;
import ru.yandex.praktikum.api_yandex_scooter.model.courier.CourierCredentials;
import ru.yandex.praktikum.api_yandex_scooter.client.CourierClient;
import ru.yandex.praktikum.api_yandex_scooter.model.courier.CourierGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CreatCourierTest {

    private CourierClient courierClient;
    private int courierId;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }
    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void clearData() {
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Check status code 201 ОК valid data of /courier") // имя теста TODO
    @Description("Check courier can be created without valid data") // описание теста TODO
    public void courierCanBeCreatedWithValidData() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true));

        courierId = courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    @DisplayName("Check status code 400 Bad Request duplicate Login error of /courier")
    @Description("Check courier recreation cannot be created with duplicate login Error")
    public void сourierReCreationCannotBeCreatedWithDuplicateLoginError() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.createCourier(courier);
        courierId = courierClient.login(CourierCredentials.from(courier))
                 .assertThat()
                 .body("id", notNullValue())
                .extract().path("id");

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется")); // TODO "message": "Этот логин уже используется. Попробуйте другой."
    }
    @Test
    @DisplayName("Check status code 201 ОК of /courier") // имя теста TODO
    @Description("Check courier can be created  without first name is Null") // описание теста TODO
    public void courierCanBeCreatedWithoutFirstNameNull() {
        Courier courier = CourierGenerator.getRandom();
        courier.setFirstName(null);

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true));

        courierId = courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .body("id", notNullValue())
                .extract().path("id");
    }
    @Test
    @DisplayName("Check status code 201 ОК without firstname of /courier")
    @Description("Check courier can be created  without firstname")
    public void courierCanBeCreatedWithoutFirstName() {
        Courier courier = CourierGenerator.getRandom();
        courier.setFirstName("");

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true));

        courierId = courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .body("id", notNullValue())
                .extract().path("id");
    }
    @Test
    @DisplayName("Check status code 400 Bad Request without Login Null of /courier")
    @Description("Check courier can be created without Login Null")
    public void courierCanBeCreatedWithoutLoginNull() {
        Courier courier = CourierGenerator.getRandom();
        courier.setLogin(null);

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Check status code 400 Bad Request without Login of /courier")
    @Description("Check courier can be created without Login")
    public void courierCanBeCreatedWithoutLogin() {
        Courier courier = CourierGenerator.getRandom();
        courier.setLogin("");

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Check status code 400 Bad Request without Password Null of /courier")
    @Description("Check courier can be created without Password Null")
    public void courierCanBeCreatedWithoutPasswordNull() {
        Courier courier = CourierGenerator.getRandom();
        courier.setPassword(null);

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
            }

    @Test
    @DisplayName("Check status code 400 Bad Request without Password of /courier")
    @Description("Check courier can be created without Password")
    public void courierCanBeCreatedWithoutPassword() {
        Courier courier = CourierGenerator.getRandom();
        courier.setPassword("");

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}