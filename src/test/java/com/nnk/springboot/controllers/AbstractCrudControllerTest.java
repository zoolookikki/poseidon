package com.nnk.springboot.controllers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.nnk.springboot.service.ICrudService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WithMockUser(username = "user", roles = "USER")
public abstract class AbstractCrudControllerTest<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    protected abstract String getEntityName();
    protected abstract ICrudService<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> getService(); 
    protected abstract DTO getDto();
    protected abstract MockMvc getMockMvc();
    protected abstract ResultActions perfomCreate(String path) throws Exception;
    protected abstract ResultActions perfomUpdate(String path) throws Exception;
    
    private String getBaseUrl(String path) {
        return "/" + getEntityName() + "/" + path;
    }
    
    private String getListViewName() {
        return getEntityName() + "/list";
    }
    
    private String getListAttributeName() {
        return getEntityName() + "s";
    }

    private String getRedirectUrl() {
        return getBaseUrl("list");
    }
    
    @Test
    @DisplayName("Accès à la liste des entités")
    void displayListTest() throws Exception {
        // given
        when((getService()).findAll()).thenReturn(List.of(getDto()));

        // when
        ResultActions result = getMockMvc().perform(get(getBaseUrl("list")));
                
        result.andExpect(status().isOk())
                .andExpect(view().name(getListViewName()))
                .andExpect(model().attributeExists(getListAttributeName()));

        verify(getService(), times(1)).findAll();
    }

    @Test
    @DisplayName("Création d'une entité valide")
    void createOkTest() throws Exception {
        // given
        when(getService().add(any())).thenReturn(getDto());
        
        // when
        ResultActions result = perfomCreate(getBaseUrl("validate"));
        
        // then
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(getRedirectUrl()));

        verify(getService(), times(1)).add(any());
    }
    
    @Test
    @DisplayName("Mise à jour d'une entité valide")
    void updateOkTest() throws Exception {
        // given
        when(getService().update(eq(1), any())).thenReturn(getDto());

        // when
        ResultActions result = perfomUpdate(getBaseUrl("update") + "/1");
        
        // then
        result.andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(getRedirectUrl()));
        
        verify(getService(), times(1)).update(eq(1), any());
    }
    
    @Test
    @DisplayName("Suppression d’une entité")
    void deleteOkTest() throws Exception {
        // given
        
        // when
        ResultActions result = getMockMvc().perform(get(getBaseUrl("delete") + "/1"));
        
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(getRedirectUrl()));

        verify(getService()).deleteById(1);
    }
}
