package app.exceptions;

public class UserException  extends Exception {

    public UserException(String msg) {
        super(msg);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
