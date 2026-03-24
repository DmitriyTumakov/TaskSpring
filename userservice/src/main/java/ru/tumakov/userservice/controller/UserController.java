package ru.tumakov.userservice.controller;

import org.springframework.hateoas.EntityModel;
import ru.tumakov.servicedata.dto.UserDTO;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;

public interface UserController {
    EntityModel<UserDTO> createUser(UserCreateRequest request);
    EntityModel<UserDTO> getUser(Long id);
    EntityModel<UserDTO> updateUser(Long id, UserUpdateRequest request);
    EntityModel<UserDTO> deleteUser(Long id);
}
