package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateRequestDto;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.dto.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface UserMapper {

    // Création d'un nouvel utilisateur depuis un UserCreateRequestDto (formulaire de création).
    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    User fromCreateRequestDto(UserCreateRequestDto userCreateRequestDto);

    // Mise à jour d’un utilisateur depuis un UserUpdateRequestDto (formulaire de modification avec mot de passe optionnel)
    User fromUpdateRequestDto(UserUpdateRequestDto userUpdateRequestDto);

    // Conversion de l'entité User vers UserDto pour l'affichage (pas de password).
    UserDto toDto(User user);

    // pour préremplir le mot de passe avec une chaîne vide.
    @Mapping(target = "password", constant = "")
    UserUpdateRequestDto toUpdateRequestDto(User user);
}
