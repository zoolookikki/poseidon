package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class AbstractCrudController<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {

    
    // méthodes à implémenter dans la classe fille.
    
    protected abstract String getEntityName(); 
    protected abstract List<DTO> findAll();
    protected abstract CREATE_DTO emptyCreateDto();
    protected abstract ENTITY getById(Integer id);
    protected abstract UPDATE_DTO getUpdateDto(ENTITY entity);
    protected abstract void deleteById(Integer id);
    protected abstract DTO add(CREATE_DTO dto);
    protected abstract DTO update(Integer id, UPDATE_DTO dto);
    
    // méthodes internes.
    
    private String view(String suffix) {
        return getEntityName() + "/" + suffix;
    }
    
    // méthodes communes.
    
    @GetMapping("/list")
    public String list(Model model) {
        log.debug("GetMapping/list");
        
        model.addAttribute(getEntityName() + "s", findAll());
        
        return view("list");
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.debug("GetMapping/showAddForm");

        model.addAttribute(getEntityName(), emptyCreateDto());
        
        return view("add");
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        log.debug("GetMapping/showUpdateForm,id="+id);
        
        ENTITY entity = getById(id);
        
        model.addAttribute(getEntityName(), getUpdateDto(entity));
        
        return view("update");
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        log.debug("GetMapping/delete,id="+id);
        
        deleteById(id);
        
        return "redirect:/" + getEntityName() + "/list";
    }
    
    // méthodes de base appelées par toutes les classes filles.
    
    protected String create(CREATE_DTO dto, BindingResult result, Model model) {
        log.debug("create,result="+result);
        
        if (result.hasErrors()) {
            model.addAttribute(getEntityName(), dto);
            return view("add");
        }
        
        add(dto);
        
        return "redirect:/" + getEntityName() + "/list";
    }
    
    protected String update(Integer id, UPDATE_DTO dto, BindingResult result, Model model) {
        log.debug("update,result="+result);
        
        if (result.hasErrors()) {
            model.addAttribute(getEntityName(), dto);
            return view("update");
        }
        
        update(id, dto);
        
        return "redirect:/" + getEntityName() + "/list";
    }
    
}
