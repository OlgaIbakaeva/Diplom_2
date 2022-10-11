import order.OrderData;
import order.OrderAPI;
import io.restassured.response.Response;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import user.UserAPI;
import user.UserData;

import static org.hamcrest.Matchers.equalTo;

public class TestsOrderCreating {
    private OrderData orderData;
    private final OrderAPI orderAPI = new OrderAPI();
    private Response response;
    private final UserAPI userAPI = new UserAPI();
    private String token;

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Создаем заказ, используя токен рандомно созданного пользователя и валидные _id ингредиентов. Возвращается код 200 и и success = true")
    public void orderWithAuthMustBeCreated() {
        UserData userData = UserData.getUserCorrect();
        response = userAPI.createUser(userData);
        token = response.then().extract().body().path("accessToken");
        orderData = OrderData.getOrderCorrect();
        response = orderAPI.createOrder(orderData, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
        userAPI.deleteUser(token);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Создаем заказ без токена авторизации с валидными _id ингредиентов. Возвращается код 200 и success = true")
    public void orderWithoutAuthMustBeCreated() {
        token = "";
        orderData = OrderData.getOrderCorrect();
        response = orderAPI.createOrder(orderData, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    /* ********* Тест "Создание заказа с ингредиентами" фактически уже реализован
       в тестах orderWithoutAuthMustBeCreated() и orderWithAuthMustBeCreated() ******** */
    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Создаем заказ с валидными _id ингредиентов. Возвращается код 200 и success = true")
    public void orderWithValidIngredientsMustBeCreated() {
        token = "";
        orderData = OrderData.getOrderCorrect();
        response = orderAPI.createOrder(orderData, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Создаем заказ с пустым списком ингредиентов. Возвращается код 400 и сообщение об ошибке")
    public void orderWithoutIngredientsIsNotCreated() {
        token = "";
        orderData = OrderData.getOrderEmpty();
        response = orderAPI.createOrder(orderData, token);
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Создаем заказ с невалидными _id ингредиентов. Возвращается код ошибки сервера 500")
    public void orderWithInvalidIngredientsIsNotCreated() {
        token = "";
        orderData = OrderData.getOrderInvalidHash();
        response = orderAPI.createOrder(orderData, token);
        response.then().assertThat().statusCode(500);
    }
}
