package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListCreateRequestDto;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.dto.BidListUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface BidListMapper {

    // Création d'une nouvelle entité depuis un formulaire de création.
    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    BidList fromCreateRequestDto(BidListCreateRequestDto bidListCreateRequestDto);

    // Conversion de l'entité BidList vers BidListDto pour l'affichage.
    BidListDto toDto(BidList bidList);

    // Pour le formulaire lors de la modification.
    BidListUpdateRequestDto toUpdateRequestDto(BidList bidlist);

}
