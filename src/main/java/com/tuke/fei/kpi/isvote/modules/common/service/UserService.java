package com.tuke.fei.kpi.isvote.modules.common.service;

import com.tuke.fei.kpi.isvote.modules.auth.dto.CreateUserDto;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.common.model.User;

import java.util.List;

public interface UserService {

    User getUser(String id);
    UserDto updateUser(String id, CreateUserDto createUserDto);
    void deleteUser(String id);
    List<User> getAll();
    // UserDto createUser(UserCreateDto userCreateDto);

}
