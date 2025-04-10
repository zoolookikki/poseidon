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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.curvePoint.CurvePointDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateRequestDto;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.service.curvePoint.ICurvePointService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/curvePoint")
@Log4j2
public class CurvePointController extends AbstractCrudController<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto> {

    private final ICurvePointService curvePointService;
    private final CurvePointMapper curvePointMapper;

    @Autowired
    public CurvePointController(ICurvePointService curvePointService, CurvePointMapper curvePointMapper) {
        this.curvePointService = curvePointService;
        this.curvePointMapper = curvePointMapper;
    }

    @Override
    protected String getEntityName() {
        return "curvePoint";
    }

    @Override
    protected CurvePointCreateRequestDto emptyCreateDto() {
        return new CurvePointCreateRequestDto();
    }

    @Override
    protected CurvePointUpdateRequestDto getUpdateDto(CurvePoint entity) {
        return curvePointMapper.toUpdateRequestDto(entity);
    }

    @Override
    protected List<CurvePointDto> findAll() {
        return curvePointService.findAll();
    }

    @Override
    protected CurvePoint getById(Integer id) {
        return curvePointService.getById(id);
    }

    @Override
    protected CurvePointDto addEntity(CurvePointCreateRequestDto dto) {
        return curvePointService.add(dto);
    }

    @Override
    protected CurvePointDto updateEntity(Integer id, CurvePointUpdateRequestDto dto) {
        return curvePointService.update(id, dto);
    }

    @Override
    protected void deleteById(Integer id) {
        curvePointService.deleteById(id);
    }

    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("curvePoint") CurvePointCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        return create(dto, result, model);
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("curvePoint") CurvePointUpdateRequestDto dto,
                                   BindingResult result,
                                   Model model) {
        return update(id, dto, result, model);
    }
}

