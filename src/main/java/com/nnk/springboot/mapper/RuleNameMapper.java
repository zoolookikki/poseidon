package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameCreateRequestDto;
import com.nnk.springboot.dto.ruleName.RuleNameDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface RuleNameMapper extends IMapper<
        RuleName,
        RuleNameDto,
        RuleNameCreateRequestDto,
        RuleNameUpdateRequestDto> {

    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    @Override
    RuleName fromCreateRequestDto(RuleNameCreateRequestDto dto);

    @Override
    RuleNameDto toDto(RuleName ruleName);

    @Override
    RuleNameUpdateRequestDto toUpdateRequestDto(RuleName ruleName);

    @Override
    // @MappingTarget permet de mettre à jour l'entité à partir de tous les champs de la dto sans perdre le contenu des autres champs.
    void updateEntityFromDto(@MappingTarget RuleName ruleName, RuleNameUpdateRequestDto dto);
    
}
