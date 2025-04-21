package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import jakarta.servlet.RequestDispatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(CustomErrorController.class)
@WithMockUser(username = "user", roles = "USER")
class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Erreur 403")
    void error403Test() throws Exception {
        // given when
        ResultActions result = mockMvc.perform(get("/error")
                // simule une requête HTTP contenant l' attribut d'erreur 403.
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 403));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "Access denied"));
    }

    @Test
    @DisplayName("Erreur 404")
    void error404Test() throws Exception {
        // given when
        ResultActions result = mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404));
        
        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "Page not found"));
    }

    @Test
    @DisplayName("Erreur 500")
    void error500Test() throws Exception {
        // given when
        ResultActions result = mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500));
        
        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "Internal server error"));
    }

    @Test
    @DisplayName("Erreur 500 avec RuntimeException") 
    void error500WithRuntimeExceptionTest() throws Exception {
        // given
        Exception ex = new RuntimeException("Exception message");
        
        // when
        ResultActions result = mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500)
                .requestAttr(RequestDispatcher.ERROR_EXCEPTION, ex));
        
        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "Exception message"));
    }

    @Test
    @DisplayName("Erreur inconnue")
    void defaultErrorTest() throws Exception {
        // given when
        ResultActions result = mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 999)); 
        
        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "An unexpected error occurred (status 999)"));
    }

    @Test
    @DisplayName("Erreur inconnue avec message")
    void defaultErrorWithMessageTest() throws Exception {
        // given when
        ResultActions result = mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 999) 
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "error 999"));
        
        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "error 999"));
    }
    
}
