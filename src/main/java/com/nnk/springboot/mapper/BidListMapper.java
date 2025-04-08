package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidList.BidListCreateRequestDto;
import com.nnk.springboot.dto.bidList.BidListDto;
import com.nnk.springboot.dto.bidList.BidListUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface BidListMapper extends IMapper<
        BidList,
        BidListDto,
        BidListCreateRequestDto,
        BidListUpdateRequestDto> {

    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    @Override
    BidList fromCreateRequestDto(BidListCreateRequestDto dto);

    @Override
    BidListDto toDto(BidList bidList);

    @Override
    BidListUpdateRequestDto toUpdateRequestDto(BidList bidList);

    @Override
    // @MappingTarget permet de mettre à jour l'entité à partir de tous les champs de la dto sans perdre le contenu des autres champs.
    void updateEntityFromDto(@MappingTarget BidList bidList, BidListUpdateRequestDto dto);
    
}
