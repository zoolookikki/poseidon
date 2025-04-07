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
    public UserDto addUser(UserCreateRequestDto dto) {
        // vérification que le username est unique.
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("The name "+dto.getUsername()+" is already in use");
        }

        User user = userMapper.fromCreateRequestDto(dto);
        // encodage du mot de passe.
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }
  
    @Override
    public UserDto updateUser(Integer id, UserUpdateRequestDto dto) {
        log.debug("update,id="+id+",userUpdateRequestDto="+dto);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("update error : id " + id + " not found"));

        // vérification que le username est unique.
        Optional<User> sameUsername = userRepository.findByUsername(dto.getUsername());
        if (sameUsername.isPresent() && !sameUsername.get().getId().equals(id)) {
            throw new UsernameAlreadyExistsException("The name "+dto.getUsername()+" is already in use");
        }

        // pour être sûr de ce que l'on modifie.
        user.setUsername(dto.getUsername());
        // ne modifier et encryter le mot de passe que si il a été saisit.
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setFullname(dto.getFullname());
        user.setRole(dto.getRole());

        return userMapper.toDto(userRepository.save(user));
    }
    
}
