import user.UserData;
import user.UserAPI;
import io.restassured.response.Response;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestsUserCreating {
    private UserData userData;
    private final UserAPI userAPI = new UserAPI();
    private Response response;

    @Test
    @DisplayName("Создать уникального пользователя")
    @Description("Регистрируется рандомно созданный пользователь. Возвращается код 200 и Bearer-токен")
    public void createdUserMustBeRegistered() {
        userData = UserData.getUserCorrect();
        response = userAPI.createUser(userData);
        response.then().assertThat().body("accessToken", notNullValue())
                .and()
                .statusCode(200);
        // удаляем тестового пользователя
        String token = response.then().extract().body().path("accessToken");
        userAPI.deleteUser(token);
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    @Description("Проверка, что нельзя создать уже зарегистрированного пользователя. Возвращается код 403 и сообщение об ошибке")
    public void createdUserDoubleIsNotRegistered() {
        userData = UserData.getUserCorrect();
        response = userAPI.createUser(userData);
        response = userAPI.createUser(userData);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Создать пользователя без email")
    @Description("Проверка, что нельзя создать пользователя без email. Возвращается код 403 и сообщение об ошибке")
    public void createdUserWithoutEmailIsNotRegistered() {
        userData = UserData.getUserWithoutEmail();
        response = userAPI.createUser(userData);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Создать пользователя без пароля")
    @Description("Проверка, что нельзя создать пользователя без пароля. Возвращается код 403 и сообщение об ошибке")
    public void createdUserWithoutPasswordIsNotRegistered() {
        userData = UserData.getUserWithoutPassword();
        response = userAPI.createUser(userData);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Создать пользователя без имени")
    @Description("Проверка, что нельзя создать пользователя без имени. Возвращается код 403 и сообщение об ошибке")
    public void createdUserWithoutNameIsNotRegistered() {
        userData = UserData.getUserWithoutName();
        response = userAPI.createUser(userData);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
}
