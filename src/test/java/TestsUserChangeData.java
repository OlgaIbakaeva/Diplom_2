import user.UserData;
import user.UserAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

public class TestsUserChangeData {
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
    @DisplayName("Изменение имени пользователя с авторизацией")
    @Description("Передаем token и данные с обновленным именем. Возвращается код 200 и success = true")
    public void UserDataNameMustBeChange() {
        str = userData.getName();
        userData.setName(str+"IO");
        response = userAPI.changeUserData(userData, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
        userData.setName(str);
        str = null;
    }

    @Test
    @DisplayName("Изменение логина пользователя с авторизацией")
    @Description("Передаем token и данные с обновленным логином (равен email). Возвращается код 200 и success = true")
    public void UserDataLoginMustBeChange() {
        str = userData.getEmail();
        userData.setEmail("IO"+str);
        response = userAPI.changeUserData(userData, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
        userData.setEmail(str);
        str = null;
    }

    @Test
    @DisplayName("Изменение пароля пользователя с авторизацией")
    @Description("Передаем token и данные с обновленным паролем. Возвращается код 200 и success = true")
    public void UserDataPasswordMustBeChange() {
        str = userData.getPassword();
        userData.setPassword(str+"1");
        response = userAPI.changeUserData(userData, token);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
        userData.setPassword(str);
        str = null;
    }

    @Test
    @DisplayName("Изменение имени пользователя без авторизации")
    @Description("Авторизуем зарегистрированного пользователя. Затем изменяем его имя. Возвращается код 401 и success = false")
    public void UserDataNameIsNotChangeWithoutAuth() {
        str = userData.getName();
        userData.setName(str+"IO");
        token = "";
        response = userAPI.changeUserData(userData, token);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
        userData.setName(str);
    }

    @Test
    @DisplayName("Изменение логина пользователя без авторизации")
    @Description("Авторизуем зарегистрированного пользователя. Затем изменяем его логин (равен email). Возвращается код 401 и success = false")
    public void UserDataLoginIsNotChangeWithoutAuth() {
        str = userData.getEmail();
        userData.setEmail("IO"+str);
        token = "";
        response = userAPI.changeUserData(userData, token);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
        userData.setEmail(str);
    }

    @Test
    @DisplayName("Изменение пароля пользователя без авторизации")
    @Description("Авторизуем зарегистрированного пользователя. Затем изменяем его пароль. Возвращается код 401 и success = false")
    public void UserDataPasswordIsNotChangeWithoutAuth() {
        str = userData.getPassword();
        userData.setPassword(str+"1");
        token = "";
        response = userAPI.changeUserData(userData, token);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
        userData.setPassword(str);
    }

    @After
    public void setDown(){
        // удаляем тестового пользователя
        userAPI.deleteUser(token);
    }
}
