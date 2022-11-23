package user.mapper;

import org.mapstruct.Mapper;
import user.dto.UserDto;
import user.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public class UserMapper {
    UserEntity toUserEntity(UserDto userDto);

    UserDto toUserDto(UserEntity userEntity);

    List<UserDto> toUserDtoList(List<UserEntity> users);
}
