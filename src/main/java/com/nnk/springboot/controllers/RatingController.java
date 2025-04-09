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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingCreateRequestDto;
import com.nnk.springboot.dto.rating.RatingDto;
import com.nnk.springboot.dto.rating.RatingUpdateRequestDto;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.service.rating.IRatingService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/rating")
@Log4j2
public class RatingController extends AbstractCrudController<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto> {

    private final IRatingService ratingService;
    private final RatingMapper ratingMapper;

    @Autowired
    public RatingController(IRatingService ratingService, RatingMapper ratingMapper) {
        this.ratingService = ratingService;
        this.ratingMapper = ratingMapper;
    }

    @Override
    protected String getEntityName() {
        return "rating";
    }

    @Override
    protected RatingCreateRequestDto emptyCreateDto() {
        return new RatingCreateRequestDto();
    }

    @Override
    protected RatingUpdateRequestDto getUpdateDto(Rating entity) {
        return ratingMapper.toUpdateRequestDto(entity);
    }

    @Override
    protected List<RatingDto> findAll() {
        return ratingService.findAll();
    }

    @Override
    protected Rating getById(Integer id) {
        return ratingService.getById(id);
    }

    @Override
    protected RatingDto add(RatingCreateRequestDto dto) {
        return ratingService.add(dto);
    }

    @Override
    protected RatingDto update(Integer id, RatingUpdateRequestDto dto) {
        return ratingService.update(id, dto);
    }

    @Override
    protected void deleteById(Integer id) {
        ratingService.deleteById(id);
    }

    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("rating") RatingCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        return create(dto, result, model);
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("rating") RatingUpdateRequestDto dto,
                                   BindingResult result,
                                   Model model) {
        return update(id, dto, result, model);
    }
}

