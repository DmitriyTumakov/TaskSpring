package ru.tumakov.userservice.request;

public record UserCreateRequest(String name, String email, String password) {
}