package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateRequestDto;
import com.nnk.springboot.dto.user.UserDto;
import com.nnk.springboot.dto.user.UserUpdateRequestDto;
import com.nnk.springboot.exception.UsernameAlreadyExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.service.user.IUserService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/user")
@Log4j2
public class UserController extends AbstractCrudController<User, UserDto, UserCreateRequestDto, UserUpdateRequestDto> {

    private final IUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(IUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // méthodes à implémenter pour la classe abtract.
    
    @Override
    protected String getEntityName() {
        return "user";
    }

    @Override
    protected List<UserDto> findAll() {
        return userService.findAll();
    }

    @Override
    protected UserCreateRequestDto emptyCreateDto() {
        return new UserCreateRequestDto();
    }

    @Override
    protected User getById(Integer id) {
        return userService.getById(id);
    }

    @Override
    protected UserUpdateRequestDto getUpdateDto(User user) {
        return userMapper.toUpdateRequestDto(user);
    }

    @Override
    protected void deleteById(Integer id) {
        userService.deleteById(id);
    }

    @Override
    protected UserDto add(UserCreateRequestDto dto) {
        return userService.add(dto);
    }

    @Override
    protected UserDto update(Integer id, UserUpdateRequestDto dto) {
        return userService.update(id, dto);
    }

    // méthodes spécifiques à l'entité User => non généralistes : username unique.
    
    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("user") UserCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        log.debug("PostMapping/submitCreateForm,userCreateRequestDto="+dto);

        try {
            return create(dto, result, model);
        } catch (UsernameAlreadyExistsException ex) {
            result.rejectValue("username", "error.user", ex.getMessage());
            return "user/add";
        }
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("user") UserUpdateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        log.debug("PostMapping/submitUpdateForm,id="+id+",userUpdateRequestDto="+dto);
        
        try {
            return update(id, dto, result, model);
        } catch (UsernameAlreadyExistsException ex) {
            result.rejectValue("username", "error.user", ex.getMessage());
            return "user/update";
        }
    }
    
}
