package com.nnk.springboot.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateRequestDto;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.dto.UserUpdateRequestDto;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.exception.UsernameAlreadyExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl extends AbstractCrudService<User, UserDto> implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           BCryptPasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected UserDto toDto(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addUser(UserCreateRequestDto userCreateRequestDto) {
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
    public UserDto updateUser(Integer id, UserUpdateRequestDto userUpdateRequestDto) {
        log.debug("update,id="+id+",userUpdateRequestDto="+userUpdateRequestDto);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("update error : id " + id + " not found"));

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
    
}
