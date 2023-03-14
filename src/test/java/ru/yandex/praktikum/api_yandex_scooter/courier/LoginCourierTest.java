package ru.yandex.praktikum.api_yandex_scooter.courier;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.*;
import ru.yandex.praktikum.api_yandex_scooter.model.courier.Courier;
import ru.yandex.praktikum.api_yandex_scooter.model.courier.CourierCredentials;
import ru.yandex.praktikum.api_yandex_scooter.client.CourierClient;
import ru.yandex.praktikum.api_yandex_scooter.model.courier.CourierGenerator;

import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.equalTo;
public class LoginCourierTest {
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
    public void loginCourierCanBeCreatedWithValidData() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.create(courier);

        courierId = courierClient.login(CourierCredentials.from(courier))
                .extract().path("id");
    }
    @Test
    public void loginCourierCanNotBeCreatedWithoutLogin() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.create(courier);

        courierId = courierClient.login(CourierCredentials.from(courier))
                .extract().path("id");

        courier.setLogin("");
        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"))
                .extract().path("id");
    }
    @Test
    public void loginCourierCanNotBeCreatedWithoutPassword() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.create(courier);

        courierId = courierClient.login(CourierCredentials.from(courier))
               .extract().path("id");

        courier.setPassword("");
        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"))
                .extract().path("id");
    }

    @Test
    public void loginCourierCanBeCreatedInvalidLoginAndPassword() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    public void loginCourierCanNotBeCreatedEmptyLogin() {
        Courier courier = CourierGenerator.getRandom();
        courier.setLogin("");

        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
    @Test
    public void loginCourierCanNotBeCreatedEmptyPassword() {
        Courier courier = CourierGenerator.getRandom();
        courier.setPassword("");

        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    public void loginCourierCanNotBeCreatedNullLogin() {
        Courier courier = CourierGenerator.getRandom();
        courier.setLogin(null);

        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
    @Test
    public void loginCourierCanNotBeCreatedNullPassword() { // тест падает 504
        Courier courier = CourierGenerator.getRandom();
        courier.setPassword(null);

        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
}
