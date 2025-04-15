package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateRequestDto;
import com.nnk.springboot.dto.user.UserDto;
import com.nnk.springboot.dto.user.UserUpdateRequestDto;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.exception.UsernameAlreadyExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.user.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private User user1;
    private User user2;
    private User user3;
    private UserDto userDto1;
    private UserDto userDto2;
    private UserCreateRequestDto userCreateDto1;
    private UserUpdateRequestDto userUpdateDto1;
    private User userUpdated1;
    private UserDto userDtoUpdated1;

    @InjectMocks
    private UserServiceImpl userService; 

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        user1 = new User(1, "John", "John@678", "Full John", "USER");
        user2 = new User(2, "Jane", "Jane@678", "Full Jane", "USER");
        user3 = new User(3, "Johnny", "BeGood@678", "Full BeGood", "USER");
        userDto1 = new UserDto(1, "John", "Full John", "USER");
        userDto2 = new UserDto(2, "Jane", "Full Jane", "USER");
        userCreateDto1 = new UserCreateRequestDto();
        userCreateDto1.setUsername("John");
        userCreateDto1.setFullname("Full John");
        userCreateDto1.setPassword("John@678");
        userCreateDto1.setRole("USER");
        userUpdateDto1 = new UserUpdateRequestDto();
        userUpdateDto1.setUsername("Johnny");
        userUpdateDto1.setFullname("Full Johnny");
        userUpdateDto1.setPassword("Johnny@8");
        userUpdateDto1.setRole("ADMIN");
        userUpdated1 = new User(1, "Johnny", "Johnny@8", "Full Johnny", "ADMIN");
        userDtoUpdated1 = new UserDto(1, "Johnny", "Full Johnny", "ADMIN");
    }

    @Test
    @DisplayName("recherche de la liste de tous les utilisateurs avec succès")
    public void findAllUsersOkTest() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        // when
        List<UserDto> result = userService.findAll();

        // then
        assertThat(result).hasSize(2)
                .containsExactly(userDto1, userDto2);
        verify(userRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("recherche de la liste de tous les utilisateurs en échec")
    public void findAllUsersFailTest() {
        // given
        when(userRepository.findAll()).thenReturn(List.of());

        // when
        List<UserDto> result = userService.findAll();

        // then
        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("recherche d'un utilisateur par son id avec succès")
    public void findUserByIdSuccessTest() {
        // given
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        // when
        User result = userService.getById(1);

        // then
        assertThat(result.getId()).isEqualTo(1);
        verify(userRepository, times(1)).findById(1);
    }
    
    @Test
    @DisplayName("recherche d'un utilisateur par son id en échec")
    public void findUserByIdFailTest() {
        // given
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> userService.getById(1))
                .isInstanceOf(EntityNotFoundException.class);
        verify(userRepository, times(1)).findById(1);
    }
    
    @Test
    @DisplayName("ajout d'un utilisateur avec succès")
    public void addOkTest() {
        // given
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.empty());
        when(userMapper.fromCreateRequestDto(userCreateDto1)).thenReturn(user1);
        when(passwordEncoder.encode("John@678")).thenReturn("$2y$10$YIJhlEJyi73CNd3hjPSX8OECdXFc7U3BtvHmDbkqTkhIhT5HFxYBq");
        when(userRepository.save(user1)).thenReturn(user1);
        when(userMapper.toDto(user1)).thenReturn(userDto1);

        // when
        UserDto result = userService.add(userCreateDto1);

        // then
        assertThat(result).isEqualTo(userDto1);
        assertThat(user1.getPassword()).isEqualTo("$2y$10$YIJhlEJyi73CNd3hjPSX8OECdXFc7U3BtvHmDbkqTkhIhT5HFxYBq");
        verify(userRepository, times(1)).save(user1);
    }
    
    @Test
    @DisplayName("ajout d'un utilisateur alors que le username existe déjà")
    public void addWhenUsernameAlreadyExistsTest() {
        // given
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

        // when then
        assertThatThrownBy(() -> userService.add(userCreateDto1))
                .isInstanceOf(UsernameAlreadyExistsException.class)
                .hasMessageContaining("already in use");
        verify(userRepository, never()).save(user1);
    }

    @Test
    @DisplayName("modification d'un utilisateur avec succès")
    public void updateOkTest() {
        // given
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(userUpdateDto1.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userUpdateDto1.getPassword())).thenReturn("$2y$10$WUTVrtVapU72P9LlKyvhku7cqJQtC/oPuZ9anHfO1NKxTgQJ.uKte");
        when(userRepository.save(any(User.class))).thenReturn(userUpdated1);
        when(userMapper.toDto(userUpdated1)).thenReturn(userDtoUpdated1);

        // when
        UserDto result = userService.update(user1.getId(), userUpdateDto1);

        // then
        assertThat(result).isEqualTo(userDtoUpdated1);
        verify(userRepository, times(1)).findById(user1.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("modification d'un utilisateur alors que le username existe déjà")
    public void updateWhenUserNameAlreadyExistsTest() {
        // given
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(userUpdateDto1.getUsername())).thenReturn(Optional.of(user3));

        // when then
        assertThatThrownBy(() -> userService.update(user1.getId(), userUpdateDto1))
                .isInstanceOf(UsernameAlreadyExistsException.class)
                .hasMessageContaining("already in use");
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("modification d'un utilisateur sans changer le username")
    public void updateWithSameUsernameTest() {
        // given
        
        // le username ne change pas.
        userUpdateDto1.setUsername(user1.getUsername());
        userUpdated1.setUsername(user1.getUsername());
        userDtoUpdated1.setUsername(user1.getUsername());
        
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(userUpdateDto1.getUsername())).thenReturn(Optional.of(user1));
        when(passwordEncoder.encode(userUpdateDto1.getPassword())).thenReturn("$2y$10$WUTVrtVapU72P9LlKyvhku7cqJQtC/oPuZ9anHfO1NKxTgQJ.uKte");
        when(userRepository.save(any(User.class))).thenReturn(userUpdated1);
        when(userMapper.toDto(userUpdated1)).thenReturn(userDtoUpdated1);

        // when
        UserDto result = userService.update(user1.getId(), userUpdateDto1);

        // then
        assertThat(result).isEqualTo(userDtoUpdated1);
        verify(userRepository, times(1)).findById(user1.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    @DisplayName("modification d'un utilisateur sans changer le mot de passe")
    public void updateWithoutPasswordModificationTest() {
        // given
        
        // le mot de passe est vide, il ne change pas.
        userUpdateDto1.setPassword("");
        userUpdated1.setPassword(user1.getPassword());
        
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(userUpdateDto1.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userUpdated1);
        when(userMapper.toDto(userUpdated1)).thenReturn(userDtoUpdated1);

        // when
        UserDto result = userService.update(user1.getId(), userUpdateDto1);

        // then
        assertThat(result).isEqualTo(userDtoUpdated1);
        verify(userRepository, times(1)).findById(user1.getId());
        verify(userRepository, times(1)).save(any(User.class));
        // important de vérifier cela.
        verify(passwordEncoder, never()).encode(any());
    }
    
    @Test
    @DisplayName("supression d'un utilisateur avec succès")
    public void deleteOkTest() {
        // given
        when(userRepository.existsById(1)).thenReturn(true);
        
        // when
        userService.deleteById(1);

        // then
        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).deleteById(1);
    }
    
    @Test
    @DisplayName("supression d'un utilisateur en échec")
    public void deleteFailTest() {
        // given
        when(userRepository.existsById(1)).thenReturn(false);

        // when then
        assertThatThrownBy(() -> userService.deleteById(1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("not found");
        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, never()).deleteById(1);
    }
    
}
