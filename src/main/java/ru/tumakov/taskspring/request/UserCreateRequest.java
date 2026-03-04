package ru.tumakov.taskspring.request;

import java.util.Optional;

public record UserCreateRequest(String name, String email, String password) {
}