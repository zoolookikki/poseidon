package com.nnk.springboot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    
    @Test
    @DisplayName("Dépassement de curveId (dépassement Integer => échec de conversion du bind)")
    void createFailWhenCurveIdTooLarge() throws Exception {
        // when
        ResultActions result = mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                // valeur trop grande pour un Integer
                .param("curveId", "8000000000") 
                .param("term", "10")
                .param("valuePoint", "30"));

        // then
        result.andExpect(status().isOk())
               // curvePoint est le nom du formulaire et curveId le champ.
              .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
              .andExpect(view().name("curvePoint/add")); // nom de la vue de formulaire
    }
    
    @Test
    @DisplayName("Dépassement de term (Double trop grand)")
    void createFailWhenTermTooLarge() throws Exception {
        verifyDoubleFieldTooLarge("term", "curvePoint/add");
    }

    @Test
    @DisplayName("Dépassement de valuePoint (Double trop grand)")
    void createFailWhenValuePointTooLarge() throws Exception {
        verifyDoubleFieldTooLarge("valuePoint", "curvePoint/add");
    }    
}
