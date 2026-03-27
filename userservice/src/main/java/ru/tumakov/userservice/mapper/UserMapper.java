package ru.tumakov.userservice.mapper;

import org.mapstruct.Mapper;
import ru.tumakov.userservice.dto.UserDTO;
import ru.tumakov.userservice.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO entityToDto(UserEntity userEntity);
}
