package com.tuke.fei.kpi.isvote.modules.common.service.impl;

import com.tuke.fei.kpi.isvote.modules.auth.dto.CreateUserDto;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserCreateDto;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.common.mapper.CommonMapper;
import com.tuke.fei.kpi.isvote.modules.common.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.tuke.fei.kpi.isvote.modules.ApiException;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.common.repository.UserRepository;
import com.tuke.fei.kpi.isvote.modules.common.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommonMapper commonMapper;
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, CommonMapper commonMapper, SecurityService securityService) {
        this.userRepository = userRepository;
        this.commonMapper = commonMapper;
        this.securityService = securityService;
    }

    @Override
    public User getUser(String id) {
        log.debug("Fetching User entity with id: {} from repository", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    throw ApiException.createObjectNotFound(String.format("User with id: %s was not found", id));
                });
    }

    @Override
    public UserDto updateUser(String id, CreateUserDto createUserDto) {
        log.info("Updating user: {}", id);
        User user = commonMapper.updateUserFromUserCreateDto(createUserDto, getUser(id));
        userRepository.save(user);
        return commonMapper.userToCurrentUserDto(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }


    public void deleteUser(String id) {
        log.info("Deleting Order: {}", id);
        userRepository.findById(id).ifPresent(userRepository::delete);
    }


}
