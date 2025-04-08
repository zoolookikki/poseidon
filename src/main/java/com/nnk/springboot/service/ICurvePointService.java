package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.CurvePointUpdateRequestDto;

public interface ICurvePointService {
    List<CurvePointDto> findAll();
    CurvePoint getById(Integer id);
    CurvePointDto addCurvePoint(CurvePointCreateRequestDto dto);
    CurvePointDto updateCurvePoint(Integer id, CurvePointUpdateRequestDto dto);
    void deleteById(Integer id);
}
