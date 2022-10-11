package user;

import config.Config;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserAPI {

    public Response createUser(UserData userData) {

        return given()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(userData)
                .post(Config.HANDLE_CREATING_USER);
    }

    public void deleteUser(String token) {
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Config.BASE_URL)
                .post(Config.HANDLE_CHANGING_USER_DATA);
    }

    public Response loginUser(UserData userData, String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Config.BASE_URL)
                .body(userData)
                .post(Config.HANDLE_LOGIN_USER);
    }

    public Response changeUserData(UserData newUserData, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Config.BASE_URL)
                .body(newUserData)
                .patch(Config.HANDLE_CHANGING_USER_DATA);
    }
}
