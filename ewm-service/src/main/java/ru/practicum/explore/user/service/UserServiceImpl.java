package ru.practicum.explore.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.repository.EventRepository;
import ru.practicum.explore.exception.ConflictException;
import ru.practicum.explore.exception.NotFoundException;
import ru.practicum.explore.request.repository.RequestRepository;
import ru.practicum.explore.user.dto.User;
import ru.practicum.explore.user.entity.UserEntity;
import ru.practicum.explore.user.mapper.UserMapper;
import ru.practicum.explore.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public User create(User user) {
        UserEntity userEntity = UserMapper.toUserEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserMapper.toUserDto(userEntity);
    }

    @Override
    public User getById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
        return UserMapper.toUserDto(userEntity);
    }

    @Override
    public List<User> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        if (ids != null) {
            List<UserEntity> userEntities = userRepository.findAllByIdIn(ids, pageable);
            return UserMapper.toUserDtoList(userEntities);
        }
        List<UserEntity> userEntities = userRepository.findAll(pageable).getContent();
        return UserMapper.toUserDtoList(userEntities);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository
                .findById(userId).orElseThrow(() -> new NotFoundException("Item not found"));
        Pageable pageable = PageRequest.of(0, 10);
        if (eventRepository.findAllByInitiatorId(userId, pageable).isEmpty()
                && requestRepository.findAllByRequesterId(userEntity.getId()).isEmpty()) {
            userRepository.delete(userEntity);
        } else {
            throw new ConflictException("user can not be deleted", "");
        }
    }

}
