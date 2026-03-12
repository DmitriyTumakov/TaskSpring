package ru.tumakov.userservice.controller.impl;

import org.springframework.web.bind.annotation.*;
import ru.tumakov.userservice.controller.UserController;
import ru.tumakov.userservice.dto.UserDTO;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;
import ru.tumakov.userservice.service.impl.DefaultUserService;

@RestController("/")
public class DefaultUserController implements UserController {
    private final DefaultUserService userService;

    public DefaultUserController(DefaultUserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @Override
    @GetMapping("/get/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @Override
    @PatchMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @Override
    @DeleteMapping("/remove/{id}")
    public UserDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
