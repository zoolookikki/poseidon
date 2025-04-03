package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateRequestDto;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    // getById plus clair que FindById car cette méthode renvoit toujours un id sinon il y a exception.
    User getById(Integer id);
    // addUser et updateUser plus clair que save (si plusieurs add.update... d'entités différentes dans le même service).
    UserDto addUser(UserCreateRequestDto userCreateRequestDto);
    UserDto updateUser(Integer id, UserUpdateRequestDto userUpdateRequestDto);
    void deleteById(Integer id);
}

