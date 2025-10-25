package Model;

public class Login{
private static final String USERNAME = "admin";
private static final String PASSWORD = "123";
public int validateLogin(String username, String password) {
    String token = username.trim();
    if (token.isEmpty()) {
        return 1;
    }
    if (password.isEmpty()) {
        return 2;
    }
    if (token.equals(USERNAME) && password.equals(PASSWORD)) {
        return 0;
    } else {
        return 3;
    }
}
}
