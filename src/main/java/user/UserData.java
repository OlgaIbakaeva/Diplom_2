package user;

import org.apache.commons.lang3.RandomStringUtils;

// рандомное создание данных пользователя, в т.ч. для негативных проверок
public class UserData {
    private String email;
    private String password;
    private String name;

    public UserData(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserData getUserCorrect() {
        return new UserData(
                RandomStringUtils.randomAlphanumeric(10)+"@mail.ru",
                "12345",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static UserData getUserWithoutEmail() {
        return new UserData(
                "",
                "12345",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static UserData getUserWithoutPassword() {
        return new UserData(
                RandomStringUtils.randomAlphanumeric(10)+"@mail.ru",
                "",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static UserData getUserWithoutName() {
        return new UserData(
                RandomStringUtils.randomAlphanumeric(10)+"@mail.ru",
                "12345",
                ""
        );
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
