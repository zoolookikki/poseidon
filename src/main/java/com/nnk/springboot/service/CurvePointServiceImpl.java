package com.nnk.springboot.service;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.CurvePointUpdateRequestDto;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint, CurvePointDto> implements ICurvePointService {

    private final CurvePointRepository curvePointRepository;
    private final CurvePointMapper curvePointMapper;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository, CurvePointMapper curvePointMapper) {
        super(curvePointRepository);
        this.curvePointRepository = curvePointRepository;
        this.curvePointMapper = curvePointMapper;
    }

    @Override
    protected CurvePointDto toDto(CurvePoint curvePoint) {
        return curvePointMapper.toDto(curvePoint);
    }

    @Override
    public CurvePointDto addCurvePoint(CurvePointCreateRequestDto dto) {
        CurvePoint curvePoint = curvePointMapper.fromCreateRequestDto(dto);
        return curvePointMapper.toDto(curvePointRepository.save(curvePoint));
    }

    @Override
    public CurvePointDto updateCurvePoint(Integer id, CurvePointUpdateRequestDto dto) {
        CurvePoint curvePoint = getById(id);
        // pour être sûr de ce que l'on modifie.
        curvePoint.setCurveId(dto.getCurveId());
        curvePoint.setTerm(dto.getTerm());
        curvePoint.setValue(dto.getValue());
        return curvePointMapper.toDto(curvePointRepository.save(curvePoint));
    }

}
