package com.nnk.springboot.service.trade;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeCreateRequestDto;
import com.nnk.springboot.dto.trade.TradeDto;
import com.nnk.springboot.dto.trade.TradeUpdateRequestDto;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TradeServiceImpl
        extends AbstractCrudService<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto>
        implements ITradeService {

    public TradeServiceImpl(
            TradeRepository tradeRepository,
            TradeMapper tradeMapper) {
        super(tradeRepository, tradeMapper);
    }
    
}
