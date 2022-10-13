import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserAPI;
import user.UserData;

import static org.hamcrest.Matchers.equalTo;

public class TestsUserLogin {
    private UserData userData;
    private final UserAPI userAPI = new UserAPI();
    private Response response;
    private String token;
    private String str;

    @Before
    public void setUp(){
        // создаем рандомно тестового пользователя, регистрируем его и сохраняем токен
        userData = UserData.getUserCorrect();
        response = userAPI.createUser(userData);
        token = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Авторизуем зарегистрированного пользователя. Возвращается код 200 и success = true")
    public void loginUserMustBeAuth() {
        response = userAPI.loginUser(userData, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Логин с неверным логином")
    @Description("Авторизуем зарегистрированного пользователя c другим email (равен логину). Возвращается код 401 и success = false")
    public void loginUserWithInvalidLoginIsNotAuth() {
        str = userData.getEmail();
        userData.setEmail("error@example.com");
        response = userAPI.loginUser(userData, token);
        userData.setEmail(str);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Авторизуем зарегистрированного пользователя c другим паролем. Возвращается код 401 и success = false")
    public void loginUserWithInvalidPasswordIsNotAuth() {
        str = userData.getPassword();
        userData.setPassword("error");
        response = userAPI.loginUser(userData, token);
        userData.setPassword(str);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @After
    public void setDown(){
        // удаляем тестового пользователя
        userAPI.deleteUser(token);
    }
}
