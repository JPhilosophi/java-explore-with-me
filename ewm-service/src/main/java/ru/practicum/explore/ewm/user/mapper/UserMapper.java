package ru.practicum.explore.ewm.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore.ewm.user.dto.UserDto;
import ru.practicum.explore.ewm.user.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserDto userDto);
    UserDto toUserDto(UserEntity userEntity);
    List<UserDto> toUserDtoList(List<UserEntity> users);
}
