package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateRequestDto;
import com.nnk.springboot.dto.user.UserDto;
import com.nnk.springboot.dto.user.UserUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface UserMapper extends IMapper<
        User,
        UserDto,
        UserCreateRequestDto,
        UserUpdateRequestDto> {

    // ici pas besoin de fromCreateRequestDto et updateEntityFromDto car les méthodes add et update du service de User sont spécifiques et donc redéfinies. 
    
    @Override
    UserDto toDto(User user);

    @Override
    // Pour le formulaire lors de la modification, spécificité pour User => préremplir le mot de passe avec une chaîne vide.
    @Mapping(target = "password", constant = "")
    UserUpdateRequestDto toUpdateRequestDto(User user);

}
