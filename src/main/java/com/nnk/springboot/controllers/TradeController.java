package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeCreateRequestDto;
import com.nnk.springboot.dto.trade.TradeDto;
import com.nnk.springboot.dto.trade.TradeUpdateRequestDto;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.service.trade.ITradeService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/trade")
@Log4j2
public class TradeController extends AbstractCrudController<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto> {

    private final ITradeService tradeService;
    private final TradeMapper tradeMapper;

    @Autowired
    public TradeController(ITradeService tradeService, TradeMapper tradeMapper) {
        this.tradeService = tradeService;
        this.tradeMapper = tradeMapper;
    }

    @Override
    protected String getEntityName() {
        return "trade";
    }

    @Override
    protected TradeCreateRequestDto emptyCreateDto() {
        return new TradeCreateRequestDto();
    }

    @Override
    protected TradeUpdateRequestDto getUpdateDto(Trade entity) {
        return tradeMapper.toUpdateRequestDto(entity);
    }

    @Override
    protected List<TradeDto> findAll() {
        return tradeService.findAll();
    }

    @Override
    protected Trade getById(Integer id) {
        return tradeService.getById(id);
    }

    @Override
    protected TradeDto add(TradeCreateRequestDto dto) {
        return tradeService.add(dto);
    }

    @Override
    protected TradeDto update(Integer id, TradeUpdateRequestDto dto) {
        return tradeService.update(id, dto);
    }

    @Override
    protected void deleteById(Integer id) {
        tradeService.deleteById(id);
    }

    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("trade") TradeCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        return create(dto, result, model);
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("trade") TradeUpdateRequestDto dto,
                                   BindingResult result,
                                   Model model) {
        return update(id, dto, result, model);
    }
}

