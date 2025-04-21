package com.nnk.springboot.service.user;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateRequestDto;
import com.nnk.springboot.dto.user.UserDto;
import com.nnk.springboot.dto.user.UserUpdateRequestDto;
import com.nnk.springboot.exception.UsernameAlreadyExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl
        extends AbstractCrudService<User, UserDto, UserCreateRequestDto, UserUpdateRequestDto>
        implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            BCryptPasswordEncoder passwordEncoder,
            MessageSource messageSource) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    // pour que le message "utilisateur déjà existant" soit internationalisé.
    private void throwUsernameExists(String username) {
        String errorMessage = messageSource.getMessage(
            "error.username.exists",
            new Object[]{username},
            LocaleContextHolder.getLocale()
        );
        throw new UsernameAlreadyExistsException(errorMessage);
    }
    
    
    /*
    add spécifique pour User car :
        - contrôle sur le userName si il existe déjà.
        - encodage du mot de passe à faire.
    */
    @Override
    public UserDto add(UserCreateRequestDto dto) {
        log.debug("add,userCreateRequestDto="+dto);
        
        // vérification que le username est unique.
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            log.debug("The name "+dto.getUsername()+" is already in use");
//            throw new UsernameAlreadyExistsException("The name "+dto.getUsername()+" is already in use");
            throwUsernameExists(dto.getUsername());
        }

        User user = userMapper.fromCreateRequestDto(dto);
        // encodage du mot de passe.
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        UserDto userDto = userMapper.toDto(userRepository.save(user));
        log.info("Entity created successfully={}", userDto);

        return userDto;
    }

    /*
    update spécifique pour User car :
         - contrôle sur le userName si il existe déjà.
         - ne pas mettre à jour le mot de passe si blanc car cela veut dire qu'il n'a pas été modifié.
    */
    @Override
    public UserDto update(Integer id, UserUpdateRequestDto dto) {
        log.debug("update,id="+id+",userUpdateRequestDto="+dto);

        User user = getById(id);

        // vérification que le username est unique.
        Optional<User> sameUsername = userRepository.findByUsername(dto.getUsername());
        if (sameUsername.isPresent() && !sameUsername.get().getId().equals(id)) {
            log.debug("The name "+dto.getUsername()+" is already in use");
//            throw new UsernameAlreadyExistsException("The name "+dto.getUsername()+" is already in use");
            throwUsernameExists(dto.getUsername());
        }

        user.setUsername(dto.getUsername());
        user.setFullname(dto.getFullname());
        user.setRole(dto.getRole());

        // ne modifier et encryter le mot de passe que si il a été saisit.
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        UserDto userDto = userMapper.toDto(userRepository.save(user)); 
        log.info("Entity updated successfully={}", userDto);

        return userDto;
    }
    
}
