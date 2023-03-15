package ru.yandex.praktikum.api_yandex_scooter.courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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

import static org.hamcrest.CoreMatchers.notNullValue;
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
    @DisplayName("Check login Courier status code 200 OK with valid data of /courier/login")
    @Description("Check login Courier can be created with valid data")
    public void loginCourierCanBeCreatedWithValidData() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.createCourier(courier);

        courierId = courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract().path("id");
    }
    @Test
    @DisplayName("Check login Courier 400 Bad Request without Login of /courier/login")
    @Description("Check login Courier can not be created without Login")
    public void loginCourierCanNotBeCreatedWithoutLogin() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.createCourier(courier);

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
    @DisplayName("Check login Courier 400 Bad Request without Password of /courier/login")
    @Description("Check login Courier can not be created without Password")
    public void loginCourierCanNotBeCreatedWithoutPassword() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.createCourier(courier);

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
    @DisplayName("Check login Courier 404 Not Found invalid Login and Password of /courier/login")
    @Description("Check login Courier can not be created invalid Login and Password")
    public void loginCourierCanNotBeCreatedInvalidLoginAndPassword() {
        Courier courier = CourierGenerator.getRandom();

        courierClient.login(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Check login Courier 400 Bad Request empty Login of /courier/login")
    @Description("Check login Courier can not be created empty Login")
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
    @DisplayName("Check login Courier 400 Bad Request empty Password of /courier/login")
    @Description("Check login Courier can not be created empty Password")
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
    @DisplayName("Check login Courier 400 Bad Request without Login Null of /courier/login")
    @Description("Check login Courier can not be created without Login Null")
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
    @DisplayName("Check login Courier 400 Bad Request without Password Null of /courier/login")
    @Description("Check login Courier can not be created without Password Null")
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
