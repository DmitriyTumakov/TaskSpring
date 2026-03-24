package ru.tumakov.userservice.service;

import ru.tumakov.servicedata.dto.UserDTO;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;

public interface UserService {
    UserDTO createUser(UserCreateRequest request);
    UserDTO getUser(Long id);
    UserDTO updateUser(Long id, UserUpdateRequest request);
    UserDTO deleteUser(Long id);
}
