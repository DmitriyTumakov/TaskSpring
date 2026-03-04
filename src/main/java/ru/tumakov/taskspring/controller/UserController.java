package ru.tumakov.taskspring.controller;

import ru.tumakov.taskspring.dto.UserDTO;
import ru.tumakov.taskspring.request.UserCreateRequest;
import ru.tumakov.taskspring.request.UserUpdateRequest;

public interface UserController {
    UserDTO createUser(UserCreateRequest request);
    UserDTO getUser(Long id);
    UserDTO updateUser(Long id, UserUpdateRequest request);
    UserDTO deleteUser(Long id);
}
