package ru.yandex.praktikum.api_yandex_scooter.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.api_yandex_scooter.client.OrdersClient;
import ru.yandex.praktikum.api_yandex_scooter.model.order.Orders;

import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

 @RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrdersClient ordersClient;
    private int orderTrack;
     private final String firstName;
     private final String lastName;
     private final String address;
     private final String metroStation;
     private final String phone;
     private final String rentTime;
     private final String deliveryDate;
     private final String comment;
     private final List<String> color ;

     public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, String rentTime,
                            String deliveryDate, String comment, List<String> color) {
         this.firstName = firstName;
         this.lastName = lastName;
         this.address = address;
         this.metroStation = metroStation;
         this.phone = phone;
         this.rentTime = rentTime;
         this.deliveryDate = deliveryDate;
         this.comment = comment;
         this.color = color;
     }
     @Parameterized.Parameters
     public static Object[][] getCreateOrderTest() {
         return new Object[][]{
                 {"Naruto", "Uchiha", "Konoha, 142 apt.", "4","+7 800 355 35 35", "5", "2023-03-06","Saske, come back to Konoha", List.of("BLACK")},
                 {"Narutoto", "Ahihcu", "Konoha, 155 apt.", "44","+7 888 777 66 55", "6", "2023-04-06","Саске, вернись в Коноху",    List.of("GREY")},
                 {"Михаил", "Гондра", "Спб, у 47 фонтана.", "7","+7 999 355 35 35", "7", "2023-05-06","Жду и верю", Arrays.asList("BLACK", "GREY")},
                 {"Гарри", "Поттер", "Bracknell, Picket Post Close, 12", "122","+7 808 353 35 35", "8", "2023-03-08","Но лучше это была метла, Нимбус-2000", null },

           };
     }
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
    @DisplayName("Check created Order 201 OK with parameterized valid data + color of /orders")
    @Description("Check created Orders with valid data + color")
    public void CreatedOrdersWithValidDataColor() {
        Orders orders = new Orders (firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

         ordersClient.createOrder(orders)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }
}
