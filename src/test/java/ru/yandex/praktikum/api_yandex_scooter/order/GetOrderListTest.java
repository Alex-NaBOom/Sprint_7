package ru.yandex.praktikum.api_yandex_scooter.order;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.praktikum.api_yandex_scooter.client.OrdersClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {

    private OrdersClient ordersClient;
    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }
    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    public void checkResponseTheOrderListIsReturn() { //TODO

        ordersClient.getListOrder(null)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("orders", notNullValue());
    }
}

