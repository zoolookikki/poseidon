package com.nnk.springboot.service.trade;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeCreateRequestDto;
import com.nnk.springboot.dto.trade.TradeDto;
import com.nnk.springboot.dto.trade.TradeUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface ITradeService extends ICrudService<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto> {}
 