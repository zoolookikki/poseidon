package com.nnk.springboot.service.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.curvePoint.CurvePointDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface ICurvePointService extends ICrudService<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto> {}
