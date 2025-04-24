package com.nnk.springboot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeCreateRequestDto;
import com.nnk.springboot.dto.trade.TradeDto;
import com.nnk.springboot.dto.trade.TradeUpdateRequestDto;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.service.ICrudService;
import com.nnk.springboot.service.trade.ITradeService;

@WebMvcTest(TradeController.class)
public class TradeControllerTest extends AbstractCrudControllerTest<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto> {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ITradeService tradeService;
    @MockitoBean private TradeMapper tradeMapper;

    private TradeDto tradeDto;

    @BeforeEach
    void setup() {
        tradeDto = new TradeDto(1, "Trade Account", "Type", 0.01d);
    }

    @Override protected String getEntityName() { return "trade"; }
    @Override protected ICrudService<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto> getService() { return tradeService; }
    @Override protected TradeDto getDto() { return tradeDto; }
    @Override protected MockMvc getMockMvc() { return mockMvc; }

    @Override
    protected ResultActions perfomCreate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("account", "Trade Account")
                .param("type",  "Type")
                .param("buyQuantity", "0.01"));
    }

    @Override
    protected ResultActions perfomUpdate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("account", "Trade Account updated")
                .param("type",  "Type updated")
                .param("buyQuantity", "0.02"));
    }
    
    @Test
    @DisplayName("Dépassement de buyQuantity (Double trop grand)")
    void createFailWhenBuyQuantityTooLarge() throws Exception {
        verifyDoubleFieldTooLarge("buyQuantity", "trade/add");
    }
    
    
}
