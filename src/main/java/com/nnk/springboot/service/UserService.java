package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateRequestDto;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    User findById(Integer id);
    UserDto save(UserCreateRequestDto userCreateRequestDto);
    UserDto update(Integer id, UserUpdateRequestDto userUpdateRequestDto);
    void deleteById(Integer id);
}

