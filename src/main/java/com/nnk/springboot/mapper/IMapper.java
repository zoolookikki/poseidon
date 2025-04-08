package com.nnk.springboot.mapper;

import org.mapstruct.MappingTarget;

public interface IMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    // pour créer une nouvelle entité depuis un formulaire de création : utilisé pour le add du service.
    ENTITY fromCreateRequestDto(CREATE_DTO dto);
    // pour convertir l'entité vers une dto pour l'affichage (uniquement les champs nécessaires).
    DTO toDto(ENTITY entity);
    // pour convertir l'entité vers une dto pour la modification d'un formulaire (uniquement les champs nécessaires).
    UPDATE_DTO toUpdateRequestDto(ENTITY entity);
    // pour mettre à jour uniquement les champs de l'entité par rapport à ceux de la dto (les autres champs de l'entité ne sont pas modifiés) : utilisé pour le update du service.
    // @MappingTarget permet de mettre à jour l'entité à partir de tous les champs de la dto sans perdre le contenu des autres champs.
    void updateEntityFromDto(@MappingTarget ENTITY entity, UPDATE_DTO dto);
    
}
