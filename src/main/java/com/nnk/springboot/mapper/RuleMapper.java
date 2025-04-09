package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.rule.RuleCreateRequestDto;
import com.nnk.springboot.dto.rule.RuleDto;
import com.nnk.springboot.dto.rule.RuleUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface RuleMapper extends IMapper<
        Rule,
        RuleDto,
        RuleCreateRequestDto,
        RuleUpdateRequestDto> {

    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    @Override
    Rule fromCreateRequestDto(RuleCreateRequestDto dto);

    @Override
    RuleDto toDto(Rule rule);

    @Override
    RuleUpdateRequestDto toUpdateRequestDto(Rule rule);

    @Override
    // @MappingTarget permet de mettre à jour l'entité à partir de tous les champs de la dto sans perdre le contenu des autres champs.
    void updateEntityFromDto(@MappingTarget Rule rule, RuleUpdateRequestDto dto);
    
}
