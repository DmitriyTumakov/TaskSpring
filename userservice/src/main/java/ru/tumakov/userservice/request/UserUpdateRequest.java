package ru.tumakov.userservice.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

public record UserUpdateRequest(
        @Schema(name = "Имя пользователя", example = "Фёдор", nullable = true)
        Optional<String> name,

        @Schema(name = "Почта", example = "fedor@mail.ru", nullable = true)
        Optional<String> email,

        @Schema(name = "Пароль", example = "12345abc", nullable = true)
        Optional<String> password) {
}