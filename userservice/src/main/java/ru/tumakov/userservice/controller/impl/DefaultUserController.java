package ru.tumakov.userservice.controller.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import ru.tumakov.userservice.controller.UserController;
import ru.tumakov.userservice.dto.UserDTO;
import ru.tumakov.userservice.exception.ServerUnavailable;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;
import ru.tumakov.userservice.service.impl.DefaultUserService;

@RestController("/user")
public class DefaultUserController implements UserController {
    private final DefaultUserService userService;

    public DefaultUserController(DefaultUserService userService) {
        this.userService = userService;
    }

    @Override
    @Operation(summary = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь создан"),
            @ApiResponse(responseCode = "400", description = "В запросе присутствует ошибка")
    })
    @CircuitBreaker(name = "createUser", fallbackMethod = "fallback")
    @PostMapping("/create")
    public EntityModel<UserDTO> createUser(@RequestBody UserCreateRequest request) {
        UserDTO userDTO = userService.createUser(request);

        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DefaultUserController.class).createUser(request))
                .withSelfRel();

        return EntityModel.of(userDTO, link);
    }

    @Override
    @Operation(summary = "Получение информации о пользователе по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @CircuitBreaker(name = "getUser", fallbackMethod = "fallback")
    @GetMapping("/get/{id}")
    public EntityModel<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUser(id);

        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DefaultUserController.class).getUser(id))
                .withSelfRel();

        return EntityModel.of(userDTO, link);
    }

    @Override
    @Operation(summary = "Обновление профиля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль обновлён"),
    })
    @CircuitBreaker(name = "updateUser", fallbackMethod = "fallback")
    @PatchMapping("/update/{id}")
    public EntityModel<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        UserDTO userDTO = userService.updateUser(id, request);

        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DefaultUserController.class).updateUser(id, request))
                .withSelfRel();

        return EntityModel.of(userDTO, link);
    }

    @Override
    @Operation(summary = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удалён"),
            @ApiResponse(responseCode = "400", description = "В запросе присутствует ошибка")
    })
    @CircuitBreaker(name = "deleteUser", fallbackMethod = "fallback")
    @DeleteMapping("/remove/{id}")
    public EntityModel<UserDTO> deleteUser(@PathVariable Long id) {
        UserDTO userDTO = userService.deleteUser(id);

        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DefaultUserController.class).deleteUser(id))
                .withSelfRel();

        return EntityModel.of(userDTO, link);
    }

    public void fallback(Throwable ex) {
        throw new ServerUnavailable("Сервис на данный момент недоступен, пожалуйста попробуйте позже.");
    }
}
