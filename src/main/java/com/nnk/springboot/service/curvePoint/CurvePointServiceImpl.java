package com.nnk.springboot.service.curvePoint;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.curvePoint.CurvePointDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateRequestDto;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2 
public class CurvePointServiceImpl
        extends AbstractCrudService<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto>
        implements ICurvePointService {

    public CurvePointServiceImpl(
            CurvePointRepository repository, 
            CurvePointMapper mapper) {
        super(repository, mapper);
    }
}
