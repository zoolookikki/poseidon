package com.nnk.springboot.service.user;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateRequestDto;
import com.nnk.springboot.dto.user.UserDto;
import com.nnk.springboot.dto.user.UserUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface IUserService extends ICrudService<User, UserDto, UserCreateRequestDto, UserUpdateRequestDto> {}
