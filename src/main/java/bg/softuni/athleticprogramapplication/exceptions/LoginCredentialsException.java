package bg.softuni.athleticprogramapplication.exceptions;

public class LoginCredentialsException extends IllegalArgumentException {
    public LoginCredentialsException(String message) {
        super(message);
    }
}
