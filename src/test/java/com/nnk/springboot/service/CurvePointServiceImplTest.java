package com.nnk.springboot.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.curvePoint.CurvePointDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateRequestDto;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.mapper.IMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.curvePoint.CurvePointServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceImplTest extends AbstractCrudServiceTest<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto> {

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private CurvePointMapper curvePointMapper;

    private CurvePoint curvePoint1, curvePoint2;
    private CurvePointDto curvePointDto1, curvePointDto2;
    private CurvePointCreateRequestDto curvePointCreateDto1;
    private CurvePointUpdateRequestDto curvePointUpdateDto1;
    private CurvePoint curvePointUpdated1;
    private CurvePointDto curvePointDtoUpdated1;

    @BeforeEach
    void setUp() {
        curvePoint1 = new CurvePoint(1, 10, 10d, 30d);
        curvePoint2 = new CurvePoint(2, 20, 20d, 60d);
        curvePointDto1 = new CurvePointDto(1, 10, 10d, 30d);
        curvePointDto2 = new CurvePointDto(2, 20, 20d, 60d);
        curvePointCreateDto1 = new CurvePointCreateRequestDto();
        curvePointCreateDto1.setCurveId(10);
        curvePointCreateDto1.setTerm(10d);
        curvePointCreateDto1.setValuePoint(30d);
        curvePointUpdateDto1 = new CurvePointUpdateRequestDto();
        curvePointUpdateDto1.setCurveId(11);
        curvePointUpdateDto1.setTerm(11d);
        curvePointUpdateDto1.setValuePoint(31d);
        curvePointUpdated1 = new CurvePoint(1, 11, 11d, 31d);
        curvePointDtoUpdated1 = new CurvePointDto(1, 11, 11d, 31d);
    }

    @Override
    protected CurvePoint getEntity() {
        return curvePoint1;
    }
    
    @Override
    protected List<CurvePoint> getEntities() {
        return List.of(curvePoint1, curvePoint2);
    }

    @Override
    protected List<CurvePointDto> getDtos() {
        return List.of(curvePointDto1, curvePointDto2);
    }

    @Override
    protected AbstractCrudService<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto> getService() {
        return curvePointService;
    }

    @Override
    protected JpaRepository<CurvePoint, Integer> getRepository() {
        return curvePointRepository;
    }

    @Override
    protected IMapper<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto> getMapper() {
        return curvePointMapper;
    }

    @Override
    protected CurvePointCreateRequestDto getCreateDto() {
        return curvePointCreateDto1;
    }
    
    @Override
    protected CurvePointDto getExpectedCreatedDto() {
        return curvePointDto1;
    }
    
    @Override
    protected CurvePointUpdateRequestDto getUpdateDto() {
        return curvePointUpdateDto1;
    }
    @Override
    protected CurvePoint getUpdatedEntity() {
        return curvePointUpdated1;
    }

    @Override
    protected CurvePointDto getExpectedUpdatedDto() {
        return curvePointDtoUpdated1;
    }
}
