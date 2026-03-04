package ru.tumakov.taskspring.mapper;

import org.mapstruct.Mapper;
import ru.tumakov.taskspring.dto.UserDTO;
import ru.tumakov.taskspring.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO entityToDto(UserEntity userEntity);
}
