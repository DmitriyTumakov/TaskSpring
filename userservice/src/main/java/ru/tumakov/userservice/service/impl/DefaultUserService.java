package ru.tumakov.userservice.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.tumakov.servicedata.event.UserEvent;
import ru.tumakov.servicedata.type.Operation;
import ru.tumakov.userservice.dto.UserDTO;
import ru.tumakov.userservice.entity.UserEntity;
import ru.tumakov.userservice.mapper.UserMapper;
import ru.tumakov.userservice.repository.UserRepository;
import ru.tumakov.userservice.request.UserCreateRequest;
import ru.tumakov.userservice.request.UserUpdateRequest;
import ru.tumakov.userservice.service.UserService;

import java.util.concurrent.CompletableFuture;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DefaultUserService(UserRepository userRepository, UserMapper userMapper, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public UserDTO createUser(UserCreateRequest request) {
        UserEntity userEntity = new UserEntity(null,
                request.name(),
                request.email(),
                request.password());
        userRepository.save(userEntity);

        if (kafkaTemplate != null) {
            UserEvent userEvent = new UserEvent(userEntity.getEmail(), Operation.CREATE);

            return getUserDTO(userEntity, userEvent);
        } else {
            return userMapper.entityToDto(userEntity);
        }
    }

    private UserDTO getUserDTO(UserEntity userEntity, UserEvent userEvent) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send("mail-sent-events-topic", userEntity.getId().toString(), userEvent);
        future.whenComplete((result, error) -> {
            if (error != null) {
                throw new IllegalArgumentException();
            }
        });
        return userMapper.entityToDto(userEntity);
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

        if (kafkaTemplate != null) {
            UserEvent userEvent = new UserEvent(userEntity.getEmail(), Operation.DELETE);
            return getUserDTO(userEntity, userEvent);
        } else {
            return userMapper.entityToDto(userEntity);
        }
    }
}
