package ru.tumakov.taskspring.service;

import ru.tumakov.taskspring.dto.UserDTO;
import ru.tumakov.taskspring.request.UserCreateRequest;
import ru.tumakov.taskspring.request.UserUpdateRequest;

public interface UserService {
    UserDTO createUser(UserCreateRequest request);
    UserDTO getUser(Long id);
    UserDTO updateUser(Long id, UserUpdateRequest request);
    UserDTO deleteUser(Long id);
}
