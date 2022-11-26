package ru.practicum.explore.user.mapper;

import ru.practicum.explore.user.dto.User;
import ru.practicum.explore.user.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }

    public static User toUserDto(UserEntity userEntity) {
        User userDto = new User();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setEmail(userEntity.getEmail());
        return userDto;
    }

    public static List<User> toUserDtoList(List<UserEntity> usersEntitys) {
        List<User> result = new ArrayList<>();

        for (UserEntity userEntity : usersEntitys) {
            result.add(toUserDto(userEntity));
        }
        return result;
    }
}
