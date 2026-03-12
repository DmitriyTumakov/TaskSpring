package ru.tumakov.userservice.controller;

import ru.tumakov.userservice.dto.UserDTO;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;

public interface UserController {
    UserDTO createUser(UserCreateRequest request);
    UserDTO getUser(Long id);
    UserDTO updateUser(Long id, UserUpdateRequest request);
    UserDTO deleteUser(Long id);
}
