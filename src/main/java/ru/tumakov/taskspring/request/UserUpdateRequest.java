package ru.tumakov.taskspring.request;

import java.util.Optional;

public record UserUpdateRequest(Optional<String> name, Optional<String> email, Optional<String> password) {
}
