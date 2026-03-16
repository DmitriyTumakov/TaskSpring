package ru.tumakov.userservice.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserCreateRequest(
        @Schema(name = "Имя пользователя", example = "Фёдор")
        String name,

        @Schema(name = "Почта", example = "fedor@mail.ru")
        String email,

        @Schema(name = "Пароль", example = "12345abc")
        String password) {
}