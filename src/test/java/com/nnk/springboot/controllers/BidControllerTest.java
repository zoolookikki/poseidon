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

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.bid.BidCreateRequestDto;
import com.nnk.springboot.dto.bid.BidDto;
import com.nnk.springboot.dto.bid.BidUpdateRequestDto;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.service.ICrudService;
import com.nnk.springboot.service.bid.IBidService;

@WebMvcTest(BidController.class)
public class BidControllerTest extends AbstractCrudControllerTest<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto> {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private IBidService bidService;
    @MockitoBean private BidMapper bidMapper;

    private BidDto bidDto;
   
    @BeforeEach
    void setup() {
        bidDto = new BidDto(1, "Account", "Type", 10d);
    }
    
    @Override protected String getEntityName() { return "bid"; }
    @Override protected ICrudService<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto> getService() { return bidService; }
    @Override protected BidDto getDto() { return bidDto; }
    @Override protected MockMvc getMockMvc() { return mockMvc; }
    
    @Override
    protected ResultActions perfomCreate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "10"));
    }

    @Override
    protected ResultActions perfomUpdate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("id", "1")
                .param("account", "Account Test Updated")
                .param("type", "Type Test Updated")
                .param("bidQuantity", "20"));
    }
    
    @Test
    @DisplayName("Dépassement de bidQuantity (Double trop grand)")
    void createFailWhenBidQuantityTooLarge() throws Exception {
        verifyDoubleFieldTooLarge("bidQuantity", "bid/add");
    }
    
}
