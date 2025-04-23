package com.nnk.springboot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingCreateRequestDto;
import com.nnk.springboot.dto.rating.RatingDto;
import com.nnk.springboot.dto.rating.RatingUpdateRequestDto;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.service.ICrudService;
import com.nnk.springboot.service.rating.IRatingService;

@WebMvcTest(RatingController.class)
public class RatingControllerTest extends AbstractCrudControllerTest<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto> {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private IRatingService ratingService;
    @MockitoBean private RatingMapper ratingMapper;

    private RatingDto ratingDto;

    @BeforeEach
    void setup() {
        ratingDto = new RatingDto(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 10);
    }

    @Override protected String getEntityName() { return "rating"; }
    @Override protected ICrudService<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto> getService() { return ratingService; }
    @Override protected RatingDto getDto() { return ratingDto; }
    @Override protected MockMvc getMockMvc() { return mockMvc; }

    @Override
    protected ResultActions perfomCreate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("moodysRating", "Moodys Rating")
                .param("sandPRating",  "Sand PRating")
                .param("fitchRating", "Fitch Rating")
                .param("orderNumber", "10"));
    }

    @Override
    protected ResultActions perfomUpdate(String path) throws Exception {
        return mockMvc.perform(post(path)
                .with(csrf())
                .param("moodysRating", "Moodys Rating updated")
                .param("sandPRating",  "Sand PRating updated")
                .param("fitchRating", "Fitch Rating updated")
                .param("orderNumber", "11"));
    }
}
