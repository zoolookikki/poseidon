package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateRequestDto;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.dto.UserUpdateRequestDto;
import com.nnk.springboot.exception.UsernameAlreadyExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;

import com.nnk.springboot.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(userMapper.toDto(user));
        }

        return userDtos;
    }

    @Override
    public User findById(Integer id) {
        // exception car si l'id n'est pas trouvé, c'est une erreur grave.
        return userRepository.findById(id)
                   .orElseThrow(() -> new UserNotFoundException("findById error : id " + id + " not found"));
    }

    @Override
    public UserDto save(UserCreateRequestDto userCreateRequestDto) {
        // vérification que le username est unique.
        if (userRepository.findByUsername(userCreateRequestDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("The name "+userCreateRequestDto.getUsername()+" is already in use");
        }

        User user = userMapper.fromCreateRequestDto(userCreateRequestDto);
        // encodage du mot de passe.
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto update(Integer id, UserUpdateRequestDto userUpdateRequestDto) {
        log.debug("update,id="+id+",userUpdateRequestDto="+userUpdateRequestDto);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("update error : id " + id + " not found"));

        // vérification que le username est unique.
        Optional<User> sameUsername = userRepository.findByUsername(userUpdateRequestDto.getUsername());
        if (sameUsername.isPresent() && !sameUsername.get().getId().equals(id)) {
            throw new UsernameAlreadyExistsException("The name "+userUpdateRequestDto.getUsername()+" is already in use");
        }

        // pour être sûr de ce que l'on modifie.
        user.setUsername(userUpdateRequestDto.getUsername());
        // ne modifier et encryter le mot de passe que si il a été saisit.
        if (userUpdateRequestDto.getPassword() != null && !userUpdateRequestDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateRequestDto.getPassword()));
        }
        user.setFullname(userUpdateRequestDto.getFullname());
        user.setRole(userUpdateRequestDto.getRole());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            // exception car si l'id n'existe pas, c'est une erreur grave.
            throw new UserNotFoundException("deleteById error : id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

}
