package order;

import config.Config;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderAPI {

    public Response createOrder(OrderData orderData, String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Config.BASE_URL)
                .body(orderData)
                .post(Config.HANDLE_ORDER);
    }

    public Response getUserOrders(String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Config.BASE_URL)
                .get(Config.HANDLE_ORDER);
    }
}
