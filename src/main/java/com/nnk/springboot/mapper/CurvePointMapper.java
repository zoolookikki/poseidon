package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.CurvePointUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface CurvePointMapper {

    // Création d'une nouvelle entité depuis un formulaire de création.
    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    CurvePoint fromCreateRequestDto(CurvePointCreateRequestDto curvePointCreateRequestDto);

    // Conversion de l'entité CurvePoint vers CurvePointDto pour l'affichage.
    CurvePointDto toDto(CurvePoint curvePoint);

    // Pour le formulaire lors de la modification.
    CurvePointUpdateRequestDto toUpdateRequestDto(CurvePoint curvePoint);

}
