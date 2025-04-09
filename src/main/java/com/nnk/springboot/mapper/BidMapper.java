package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.bid.BidCreateRequestDto;
import com.nnk.springboot.dto.bid.BidDto;
import com.nnk.springboot.dto.bid.BidUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface BidMapper extends IMapper<
        Bid,
        BidDto,
        BidCreateRequestDto,
        BidUpdateRequestDto> {

    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    @Override
    Bid fromCreateRequestDto(BidCreateRequestDto dto);

    @Override
    BidDto toDto(Bid bid);

    @Override
    BidUpdateRequestDto toUpdateRequestDto(Bid bid);

    @Override
    // @MappingTarget permet de mettre à jour l'entité à partir de tous les champs de la dto sans perdre le contenu des autres champs.
    void updateEntityFromDto(@MappingTarget Bid bid, BidUpdateRequestDto dto);
    
}
