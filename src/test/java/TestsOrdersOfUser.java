import order.OrderData;
import order.OrderAPI;
import io.restassured.response.Response;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import user.UserAPI;
import user.UserData;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestsOrdersOfUser {
    private OrderData orderData;
    private final OrderAPI orderAPI = new OrderAPI();
    private Response response;
    private final UserAPI userAPI = new UserAPI();
    private String token;

    @Test
    @DisplayName("Получение заказов конкретного пользователя (авторизованный пользователь)")
    @Description("Сценарий: регистрация пользователя; создание заказов с токеном; получение списка заказов по токену. Возвращается код 200 и список заказов")
    public void listOfOrdersAuthUserMustBeReceived() {
        UserData userData = UserData.getUserCorrect();
        response = userAPI.createUser(userData);
        token = response.then().extract().body().path("accessToken");
        orderData = OrderData.getOrderCorrect();
        response = orderAPI.createOrder(orderData, token);
        response = orderAPI.createOrder(orderData, token);
        response = orderAPI.createOrder(orderData, token);
        response = orderAPI.getUserOrders(token);
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
        userAPI.deleteUser(token);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя (неавторизованный пользователь)")
    @Description("Сценарий: создание заказов без токена; получение списка заказов. Возвращается код 401 и сообщение об ошибке")
    public void listOfOrdersWithoutAuthIsNotReceived() {
        token = "";
        orderData = OrderData.getOrderCorrect();
        response = orderAPI.createOrder(orderData, token);
        response = orderAPI.createOrder(orderData, token);
        response = orderAPI.createOrder(orderData, token);
        response = orderAPI.getUserOrders(token);
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
