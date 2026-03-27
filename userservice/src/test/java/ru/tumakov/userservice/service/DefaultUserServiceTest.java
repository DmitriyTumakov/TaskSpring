package ru.tumakov.userservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.tumakov.userservice.dto.UserDTO;
import ru.tumakov.userservice.entity.UserEntity;
import ru.tumakov.userservice.mapper.UserMapper;
import ru.tumakov.userservice.repository.UserRepository;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;
import ru.tumakov.userservice.service.impl.DefaultUserService;

import java.util.NoSuchElementException;
import java.util.Optional;

class DefaultUserServiceTest {

    @InjectMocks
    private DefaultUserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        UserEntity expected = new UserEntity(null, "Test", "test@mail.ru", "test");
        Mockito.when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(expected);
        Mockito.when(userMapper.entityToDto(ArgumentMatchers.any(UserEntity.class))).thenReturn(new UserDTO(1L, "Test", "test@mail.ru", "test"));

        UserDTO result = userService.createUser(
                new UserCreateRequest(expected.getName(), expected.getEmail(), expected.getPassword()));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected.getName(), result.getName());
        Assertions.assertEquals(expected.getEmail(), result.getEmail());
    }

    @Test
    void getUser_success() {
        UserEntity expected = new UserEntity(1L, "Test", "test@mail.ru", "test");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(expected));
        Mockito.when(userMapper.entityToDto(ArgumentMatchers.any(UserEntity.class))).thenReturn(new UserDTO(1L, "Test", "test@mail.ru", "test"));

        UserDTO result = userService.getUser(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected.getName(), result.getName());
        Assertions.assertEquals(expected.getEmail(), result.getEmail());
    }

    @Test
    void getUser_userNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(userMapper.entityToDto(ArgumentMatchers.any(UserEntity.class))).thenReturn(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> userService.getUser(1L));
    }

    @Test
    void updateUser() {
        UserEntity before = new UserEntity(1L, "OldTest", "test@mail.ru", "test");
        UserEntity expected = new UserEntity(1L, "NewTest", "test@mail.ru", "test");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(expected));
        Mockito.when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(expected);
        Mockito.when(userMapper.entityToDto(ArgumentMatchers.any(UserEntity.class))).thenReturn(new UserDTO(1L, "NewTest", "test@mail.ru", "test"));

        UserDTO result = userService.updateUser(1L,
                new UserUpdateRequest(Optional.of("NewTest"), Optional.empty(), Optional.empty()));

        Assertions.assertEquals(expected.getName(), result.getName());
    }

    @Test
    void deleteUser_success() {
        UserEntity test = new UserEntity(1L, "Test", "test.mail.ru", "test");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(test));
        Mockito.when(userMapper.entityToDto(ArgumentMatchers.any(UserEntity.class))).thenReturn(new UserDTO(1L, "Test", "test@mail.ru", "test"));

        userService.deleteUser(1L);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> userService.getUser(1L));
    }

    @Test
    void deleteUser_userNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(userMapper.entityToDto(ArgumentMatchers.any(UserEntity.class))).thenReturn(null);

        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> userService.deleteUser(1L));
    }
}