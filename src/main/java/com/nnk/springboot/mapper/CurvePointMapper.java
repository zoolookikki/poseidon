package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.curvePoint.CurvePointDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface CurvePointMapper extends IMapper<
        CurvePoint,
        CurvePointDto,
        CurvePointCreateRequestDto,
        CurvePointUpdateRequestDto> {

    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    @Override
    CurvePoint fromCreateRequestDto(CurvePointCreateRequestDto dto);

    @Override
    CurvePointDto toDto(CurvePoint entity);

    @Override
    CurvePointUpdateRequestDto toUpdateRequestDto(CurvePoint entity);

    @Override
    // @MappingTarget permet de mettre à jour l'entité à partir de tous les champs de la dto sans perdre le contenu des autres champs.
    void updateEntityFromDto(@MappingTarget CurvePoint entity, CurvePointUpdateRequestDto dto);
    
}
