package com.nnk.springboot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointCreateRequestDto;
import com.nnk.springboot.dto.curvePoint.CurvePointDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateRequestDto;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.service.ICrudService;
import com.nnk.springboot.service.curvePoint.ICurvePointService;

@WebMvcTest(CurvePointController.class)
public class CurvePointControllerTest extends AbstractCrudControllerTest<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto> {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ICurvePointService curvePointService;
    @MockitoBean private CurvePointMapper curvePointMapper;

    private CurvePointDto curvePointDto;

    @BeforeEach
    void setup() {
        curvePointDto = new CurvePointDto(1, 10, 10d, 30d);
    }

    @Override protected String getEntityName() { return "curvePoint"; }
    @Override protected ICrudService<CurvePoint, CurvePointDto, CurvePointCreateRequestDto, CurvePointUpdateRequestDto> getService() { return curvePointService; }
    @Override protected CurvePointDto getDto() { return curvePointDto; }
    @Override protected MockMvc getMockMvc() { return mockMvc; }

    @Override
    protected ResultActions perfomCreate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("curveId", "10")
                .param("term", "10")
                .param("valuePoint", "30"));
    }

    @Override
    protected ResultActions perfomUpdate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("id", "1")
                .param("curveId", "20")
                .param("term", "20")
                .param("valuePoint", "60"));
    }
}
