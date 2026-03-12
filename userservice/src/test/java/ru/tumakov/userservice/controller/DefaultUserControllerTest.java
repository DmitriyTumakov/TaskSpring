package ru.tumakov.userservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tumakov.userservice.dto.UserDTO;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;
import ru.tumakov.userservice.service.impl.DefaultUserService;

import java.util.Optional;

@Testcontainers
@SpringBootTest
@AutoConfigureWebTestClient
class DefaultUserControllerTest {

    @Autowired
    private WebTestClient client;
    @Autowired
    private DefaultUserService defaultUserService;

    @Container
    private static final GenericContainer<?> postgres = new GenericContainer<>("postgres:13")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_USER", "postgres")
            .withEnv("POSTGRES_PASSWORD", "password");
    String jdbcUrl = String.format("jdbc:postgresql://%s:%d/postgres",
            postgres.getHost(),
            postgres.getMappedPort(5432));

    @Test
    void createUser() {
        UserCreateRequest request = new UserCreateRequest("test", "test@mail.ru", "testpassword");

        UserDTO result = client.post().uri("/create")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class).returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.name(), result.name());
        Assertions.assertEquals(request.email(), result.email());
        Assertions.assertEquals(request.password(), result.password());
    }

    @Test
    void getUser() {
        UserCreateRequest request = new UserCreateRequest("test", "test@mail.ru", "testpassword");
        UserDTO user = defaultUserService.createUser(request);

        UserDTO result = client.get().uri("/get/{id}", user.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class).returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.name(), result.name());
        Assertions.assertEquals(request.email(), result.email());
        Assertions.assertEquals(request.password(), result.password());
    }

    @Test
    void updateUser() {
        UserCreateRequest request = new UserCreateRequest("test", "test@mail.ru", "testpassword");
        UserUpdateRequest updateRequest = new UserUpdateRequest(
                Optional.of("newTest"),
                Optional.of("newtest@mail.ru"),
                Optional.of("newtestpassword"));
        UserDTO user = defaultUserService.createUser(request);

        UserDTO result = client.patch().uri("/update/{id}", user.id())
                .contentType(MediaType.APPLICATION_JSON).bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class).returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updateRequest.name().get(), result.name());
        Assertions.assertEquals(updateRequest.email().get(), result.email());
        Assertions.assertEquals(updateRequest.password().get(), result.password());
    }

    @Test
    void deleteUser() {
        UserCreateRequest request = new UserCreateRequest("test", "test@mail.ru", "testpassword");
        UserDTO user = defaultUserService.createUser(request);

        client.delete().uri("/remove/{id}", user.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class).returnResult()
                .getResponseBody();
        client.get().uri("/get/{id}", user.id())
                .exchange()
                .expectStatus().isNotFound();
    }
}