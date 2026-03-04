package ru.tumakov.taskspring.service.impl;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import ru.tumakov.taskspring.dto.UserDTO;
import ru.tumakov.taskspring.entity.UserEntity;
import ru.tumakov.taskspring.mapper.UserMapper;
import ru.tumakov.taskspring.repository.UserRepository;
import ru.tumakov.taskspring.request.UserCreateRequest;
import ru.tumakov.taskspring.request.UserUpdateRequest;
import ru.tumakov.taskspring.service.UserService;

import java.util.NoSuchElementException;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public DefaultUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserCreateRequest request) {
        UserEntity userEntity = new UserEntity(null,
                request.name(),
                request.email(),
                request.password());
        return userMapper.entityToDto(userRepository.save(userEntity));
    }

    @Override
    public UserDTO getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        return userMapper.entityToDto(userEntity);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateRequest request) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        request.name().ifPresent(userEntity::setName);
        request.email().ifPresent(userEntity::setEmail);
        request.password().ifPresent(userEntity::setPassword);
        return userMapper.entityToDto(userRepository.save(userEntity));
    }

    @Override
    public UserDTO deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        userRepository.delete(userEntity);
        return userMapper.entityToDto(userEntity);
    }
}
