package ru.tumakov.userservice.exception;

public class ServerUnavailable extends RuntimeException {
    public ServerUnavailable() {}

    public ServerUnavailable(String message) {
        super(message);
    }

    public ServerUnavailable(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerUnavailable(Throwable cause) {
        super(cause);
    }
}
