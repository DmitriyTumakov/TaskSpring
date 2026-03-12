package ru.tumakov.event;


import ru.tumakov.type.Operation;

public class UserEvent {
    private String email;
    private Operation operation;

    public UserEvent() {}

    public UserEvent(String email, Operation operation) {
        this.email = email;
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public Operation getOperation() {
        return operation;
    }
}
