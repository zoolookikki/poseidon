package com.nnk.springboot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

//.... à revoir pourquoi cela ne s'importe pas automatiquement :
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.service.user.IUserService;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateRequestDto;
import com.nnk.springboot.dto.user.UserDto;
import com.nnk.springboot.dto.user.UserUpdateRequestDto;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.exception.UsernameAlreadyExistsException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(UserController.class)
@WithMockUser(username = "admin", roles = "ADMIN")
public class UserControllerTest {

    private static final String BASE_URL = "/user";
    private static final String REDIRECT_LIST = BASE_URL + "/list";

    private User user;
    private UserDto userDto;
    private UserDto otherUserDto;
    private UserCreateRequestDto userCreateDto;
    private UserUpdateRequestDto userUpdateDto;
    
    // Pour que MockMvc soit injecté dans le test pour pouvoir l’utiliser.
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    @MockitoBean
    private UserMapper userMapper;

    private ResultActions performCreateRequest(UserCreateRequestDto dto) throws Exception {
        return mockMvc.perform(post(BASE_URL + "/validate")
                .with(csrf())
                .param("username", dto.getUsername())
                .param("fullname", dto.getFullname())
                .param("password", dto.getPassword())
                .param("role", dto.getRole()));
    }
    
    private ResultActions performUpdateRequest(int id, UserUpdateRequestDto dto) throws Exception {
        return mockMvc.perform(post(BASE_URL + "/update/" + id)
                .with(csrf())
                .param("id", String.valueOf(dto.getId()))
                .param("username", dto.getUsername())
                .param("fullname", dto.getFullname())
                .param("password", dto.getPassword())
                .param("role", dto.getRole()));
    }
    
    @BeforeEach
    void setup() {
        user = new User(1, "John", "$2y$10$i5ABiqllUidvg7/zbN.uaOqdke6TUdYs4J/HrKPXmQ86fVe4.j3mG", "Full John", "USER");

        userDto = new UserDto(1, "John", "Full John", "USER");
        otherUserDto = new UserDto(2, "Jane", "Full Jane", "ADMIN");
        
        userCreateDto = new UserCreateRequestDto();
        userCreateDto.setUsername("John");
        userCreateDto.setFullname("Full John");
        userCreateDto.setPassword("John@678");
        userCreateDto.setRole("USER");

        userUpdateDto = new UserUpdateRequestDto();
        userUpdateDto.setId(1);
        userUpdateDto.setUsername("John");
        userUpdateDto.setFullname("Full John");
        // vide par défaut.
        userUpdateDto.setPassword(""); 
        userUpdateDto.setRole("USER");
    }
    
    @Test
    @DisplayName("Accès à la liste des utilisateurs")
    void displayListTest() throws Exception {
        // given
        when(userService.findAll()).thenReturn(List.of(userDto, otherUserDto));
    
        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + "/list"));
        
        // then
        result.andExpect(status().isOk())
            // vérifier que le contrôleur renvoit bien cette vue.
            .andExpect(view().name("user/list"))
            // pour être sûr que l'attibut users soit bien présent dans le model pour que Thymeleaf puisse faire le th:each.
            .andExpect(model().attributeExists("users"));
        
        // pour être sûr que la fonction findAll a bien été appelée.
        verify(userService, times(1)).findAll();
    }

    @Test
    @DisplayName("Accès à la page de création (/user/add)")
    void displayAddFormTest() throws Exception {
        // given
        
        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + "/add"));
        
        // then
        result.andExpect(status().isOk())
            .andExpect(view().name("user/add"))
            .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("Création d’un utilisateur valide (/user/validate)")
    void createOkTest() throws Exception {
        // given
        when(userService.add(any(UserCreateRequestDto.class))).thenReturn(userDto);

        // when
        ResultActions result = performCreateRequest(userCreateDto);
        
        // then
        // quand c'est ok, on s'attend à une redirection.
        result.andExpect(status().is3xxRedirection())
        // vers /user/list
            .andExpect(redirectedUrl(REDIRECT_LIST));

        verify(userService, times(1)).add(any(UserCreateRequestDto.class));
    }

    @Test
    @DisplayName("Accès à la page de mise à jour (/user/update/{id})")
    void displayUpdateFormTest() throws Exception {
        // given
        when(userService.getById(1)).thenReturn(user);
        when(userMapper.toUpdateRequestDto(user)).thenReturn(userUpdateDto);

        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + "/update/1"));
        
        // then
        result.andExpect(status().isOk())
            .andExpect(view().name("user/update"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attribute("user", hasProperty("id", is(userUpdateDto.getId()))))
            .andExpect(model().attribute("user", hasProperty("username", is(userUpdateDto.getUsername()))))
            .andExpect(model().attribute("user", hasProperty("fullname", is(userUpdateDto.getFullname()))))
            .andExpect(model().attribute("user", hasProperty("password", is(userUpdateDto.getPassword()))))
            .andExpect(model().attribute("user", hasProperty("role", is(userUpdateDto.getRole()))));
        
        verify(userService, times(1)).getById(1);
    }
    
    
    @Test
    @DisplayName("Mise à jour valide (/user/update/{id})")
    void updateOkTest() throws Exception {
        // given
        // eq(1) au lieu de 1 car Mockito interdit de mixer une valeur brute et un argument matcher.
        // inutile car le retour de userService.update n'est pas utilisé pour le moment mais au cas ou.
        when(userService.update(eq(1), any(UserUpdateRequestDto.class))).thenReturn(userDto);

        // when
        ResultActions result = performUpdateRequest(1, userUpdateDto);
        
        // then
        result.andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(REDIRECT_LIST));
       
        verify(userService, times(1)).update(eq(1), any(UserUpdateRequestDto.class));
    }

    @Test
    @DisplayName("Mise à jour avec id inexistant")
    void updateIdNotFoundFailTest() throws Exception {
        // given 
        doThrow(new EntityNotFoundException("userId 1 not found"))
            .when(userService).update(eq(1), any(UserUpdateRequestDto.class));
    
        // when
        ResultActions result = performUpdateRequest(1, userUpdateDto);
        
        // then
        result.andExpect(status().isOk())
            .andExpect(view().name("error"))
            .andExpect(model().attribute("errorMessage", containsString("userId 1 not found")));
        
        verify(userService, times(1)).update(eq(1), any(UserUpdateRequestDto.class));
    }

    @Test
    @DisplayName("Suppression d'un id (/user/delete/{id})")
    void deleteOkTest() throws Exception {
        // given
        
        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + "/delete/1"));
        
        // then
        result.andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(REDIRECT_LIST));

        verify(userService, times(1)).deleteById(1);
    }    
    
    @Test
    @DisplayName("Suppression d'un id inexistant")
    void deleteFailTest() throws Exception {
        // given
        doThrow(new EntityNotFoundException("userId 1 not found"))
                .when(userService).deleteById(1);
    
        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + "/delete/1"));
        
        // then
        result.andExpect(status().isOk())
            .andExpect(view().name("error"))
            .andExpect(model().attribute("errorMessage", containsString("userId 1 not found")));
        
        verify(userService, times(1)).deleteById(1);
    }
    
    @Test
    @DisplayName("UserName déjà utilisé")
    void userNameAlreadyExistsTest() throws Exception {
        // given
        doThrow(new UsernameAlreadyExistsException("The name John is already in use")) 
                .when(userService).add(any(UserCreateRequestDto.class));

        // when 
        ResultActions result = performCreateRequest(userCreateDto);
        
        // then
        result.andExpect(model().attributeHasFieldErrors("user", "username"))
            .andExpect(view().name("user/add"));

        verify(userService, times(1)).add(any(UserCreateRequestDto.class));
    }
    
    // ici pas besoin de faire les mises à jour invalides car même résultat => voir CommonRequestUserDto.
    @Test
    @DisplayName("Création invalide (formulaire vide)")
    void createEmptyFormFailTest() throws Exception {
        // given
        UserCreateRequestDto emptyDto = new UserCreateRequestDto();
        emptyDto.setUsername("");
        emptyDto.setFullname("");
        emptyDto.setPassword("");
        emptyDto.setRole("");
        
        // when
        ResultActions result = performCreateRequest(emptyDto);
        
        // then
        result.andExpect(model().attributeHasFieldErrors("user", "username", "fullname", "password", "role"))
            .andExpect(view().name("user/add"));

        verify(userService, never()).add(any(UserCreateRequestDto.class));
    }
    
    @Test
    @DisplayName("Création invalide (username invalide)")
    void createInvalidUserNameTest() throws Exception {
        // given
        
        // when
        ResultActions result = mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "John++")
                        .param("fullname", "Full John")
                        .param("password", "John@678")
                        .param("role", "USER"));
        
        // then
        result.andExpect(model().attributeHasFieldErrors("user", "username"))
            .andExpect(view().name("user/add"));

        verify(userService, never()).add(any(UserCreateRequestDto.class));
    }
    
    @Test
    @DisplayName("Création invalide (fullname invalide)")
    void createInvalidUserFullNameTest() throws Exception {
        // given
        
        // when
        ResultActions result = mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "John")
                        .param("fullname", "Full@John")
                        .param("password", "John@678")
                        .param("role", "USER"));

        // then
        result.andExpect(model().attributeHasFieldErrors("user", "fullname"))
            .andExpect(view().name("user/add"));
        
        verify(userService, never()).add(any(UserCreateRequestDto.class));
    }
    
    @Test
    @DisplayName("Création invalide (password invalide)")
    void createInvalidPasswordTest() throws Exception {
        // given
        
        // when
        ResultActions result = mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "John")
                        .param("fullname", "FullJohn")
                        .param("password", "John")
                        .param("role", "USER"));
        
        // then
        result.andExpect(model().attributeHasFieldErrors("user", "password"))
            .andExpect(view().name("user/add"));

        verify(userService, never()).add(any(UserCreateRequestDto.class));
    }
    
    @Test
    @DisplayName("Création invalide (role invalide)")
    void createInvalidRoleTest() throws Exception {
        // given
        
        // when
        ResultActions result = mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "John")
                        .param("fullname", "Full John")
                        .param("password", "John@678")
                        .param("role", "XXXXX"));
        
        // then
        result.andExpect(model().attributeHasFieldErrors("user", "role"))
            .andExpect(view().name("user/add"));

        verify(userService, never()).add(any(UserCreateRequestDto.class));
    }
    
}
