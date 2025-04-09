package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeCreateRequestDto;
import com.nnk.springboot.dto.trade.TradeDto;
import com.nnk.springboot.dto.trade.TradeUpdateRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//gràce à cette annotation, mapstruct va générer une implémentation de cette interface automatique lors de la compilation.
@Mapper(componentModel = "spring")
public interface TradeMapper extends IMapper<
        Trade,
        TradeDto,
        TradeCreateRequestDto,
        TradeUpdateRequestDto> {

    // Pour supprimer le warning à la compilation car c'est volontaire (l'id sera généré lors de l'insertion dans la base).
    @Mapping(target = "id", ignore = true)
    @Override
    Trade fromCreateRequestDto(TradeCreateRequestDto dto);

    @Override
    TradeDto toDto(Trade trade);

    @Override
    TradeUpdateRequestDto toUpdateRequestDto(Trade trade);

    @Override
    // @MappingTarget permet de mettre à jour l'entité à partir de tous les champs de la dto sans perdre le contenu des autres champs.
    void updateEntityFromDto(@MappingTarget Trade trade, TradeUpdateRequestDto dto);
    
}
