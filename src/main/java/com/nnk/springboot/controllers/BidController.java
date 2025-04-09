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

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.bid.BidCreateRequestDto;
import com.nnk.springboot.dto.bid.BidDto;
import com.nnk.springboot.dto.bid.BidUpdateRequestDto;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.service.bid.IBidService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/bid")
@Log4j2
public class BidController extends AbstractCrudController<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto> {

    private final IBidService bidService;
    private final BidMapper bidMapper;

    @Autowired
    public BidController(IBidService bidService, BidMapper bidMapper) {
        this.bidService = bidService;
        this.bidMapper = bidMapper;
    }

    @Override
    protected String getEntityName() {
        return "bid";
    }

    @Override
    protected BidCreateRequestDto emptyCreateDto() {
        return new BidCreateRequestDto();
    }

    @Override
    protected BidUpdateRequestDto getUpdateDto(Bid entity) {
        return bidMapper.toUpdateRequestDto(entity);
    }

    @Override
    protected List<BidDto> findAll() {
        return bidService.findAll();
    }

    @Override
    protected Bid getById(Integer id) {
        return bidService.getById(id);
    }

    @Override
    protected BidDto add(BidCreateRequestDto dto) {
        return bidService.add(dto);
    }

    @Override
    protected BidDto update(Integer id, BidUpdateRequestDto dto) {
        return bidService.update(id, dto);
    }

    @Override
    protected void deleteById(Integer id) {
        bidService.deleteById(id);
    }

    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("bid") BidCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        return create(dto, result, model);
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("bid") BidUpdateRequestDto dto,
                                   BindingResult result,
                                   Model model) {
        return update(id, dto, result, model);
    }
}

