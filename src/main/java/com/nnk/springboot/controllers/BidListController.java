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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.dto.BidListCreateRequestDto;
import com.nnk.springboot.dto.BidListUpdateRequestDto;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.service.IBidListService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/bidList")
@Log4j2
public class BidListController extends AbstractCrudController<BidList, BidListDto, BidListCreateRequestDto, BidListUpdateRequestDto> {

    private final IBidListService bidListService;
    private final BidListMapper bidListMapper;

    @Autowired
    public BidListController(IBidListService bidListService, BidListMapper bidListMapper) {
        this.bidListService = bidListService;
        this.bidListMapper = bidListMapper;
    }

    @Override
    protected String getEntityName() {
        return "bidList";
    }

    @Override
    protected BidListCreateRequestDto emptyCreateDto() {
        return new BidListCreateRequestDto();
    }

    @Override
    protected BidListUpdateRequestDto getUpdateDto(BidList entity) {
        return bidListMapper.toUpdateRequestDto(entity);
    }

    @Override
    protected List<BidListDto> findAll() {
        return bidListService.findAll();
    }

    @Override
    protected BidList getById(Integer id) {
        return bidListService.getById(id);
    }

    @Override
    protected BidListDto add(BidListCreateRequestDto dto) {
        return bidListService.addBidList(dto);
    }

    @Override
    protected BidListDto update(Integer id, BidListUpdateRequestDto dto) {
        return bidListService.updateBidList(id, dto);
    }

    @Override
    protected void deleteById(Integer id) {
        bidListService.deleteById(id);
    }

    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("bidList") BidListCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        return create(dto, result, model);
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("bidList") BidListUpdateRequestDto dto,
                                   BindingResult result,
                                   Model model) {
        return update(id, dto, result, model);
    }
}

