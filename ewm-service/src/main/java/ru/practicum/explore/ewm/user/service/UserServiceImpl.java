package ru.practicum.explore.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.exeption.ConflictException;
import ru.practicum.explore.ewm.exeption.NotFoundException;
import ru.practicum.explore.ewm.user.dto.UserDto;
import ru.practicum.explore.ewm.user.entity.UserEntity;
import ru.practicum.explore.ewm.user.mapper.UserMapper;
import ru.practicum.explore.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto user) {
        try {
            UserEntity userEntity = userRepository.save(userMapper.toUserEntity(user));
            return userMapper.toUserDto(userEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("User email already in exists", "email = " + user.getEmail());
        }
    }

    @Override
    public List<UserDto> getAll(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<UserEntity> userEntities;
        if (ids != null) {
            userEntities = userRepository.findAllByIdIn(ids, pageable);
            return userMapper.toUserDtoList(userEntities);
        }
        userEntities = userRepository.findAll(pageable).getContent();
        return userMapper.toUserDtoList(userEntities);
    }

    @Override
    public void deleteById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("can't fine user"));
        userRepository.delete(userEntity);
    }

    @Override
    public UserDto getById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("can't fine user"));
        return userMapper.toUserDto(userEntity);
    }
}
