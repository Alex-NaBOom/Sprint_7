package ru.yandex.praktikum.api_yandex_scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.api_yandex_scooter.client.base.ScooterRestClient;
import ru.yandex.praktikum.api_yandex_scooter.model.order.ListOrders;
import ru.yandex.praktikum.api_yandex_scooter.model.order.Orders;

import static io.restassured.RestAssured.given;

public class OrdersClient extends ScooterRestClient {
    private static final String ORDERS_URI = BASE_URI + "orders/";

    @Step("Create order {order}")
    public ValidatableResponse createOrder(Orders orders) {
        return given()
                .spec(getBaseReqSpec())
                .body(orders)
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step("Get a list of orders as {listOrder}")   // TODO
    public ValidatableResponse getListOrder(ListOrders listOrders) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(ORDERS_URI)
                .then();
    }


}
