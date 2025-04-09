package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameCreateRequestDto;
import com.nnk.springboot.dto.ruleName.RuleNameDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateRequestDto;
import com.nnk.springboot.exception.RuleNameAlreadyExistsException;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.service.ruleName.IRuleNameService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/ruleName")
@Log4j2
public class RuleNameController extends AbstractCrudController<RuleName, RuleNameDto, RuleNameCreateRequestDto, RuleNameUpdateRequestDto> {

    private final IRuleNameService ruleNameService;
    private final RuleNameMapper ruleNameMapper;

    @Autowired
    public RuleNameController(IRuleNameService ruleNameService, RuleNameMapper ruleNameMapper) {
        this.ruleNameService = ruleNameService;
        this.ruleNameMapper = ruleNameMapper;
    }

    @Override
    protected String getEntityName() {
        return "ruleName";
    }

    @Override
    protected RuleNameCreateRequestDto emptyCreateDto() {
        return new RuleNameCreateRequestDto();
    }

    @Override
    protected RuleNameUpdateRequestDto getUpdateDto(RuleName entity) {
        return ruleNameMapper.toUpdateRequestDto(entity);
    }

    @Override
    protected List<RuleNameDto> findAll() {
        return ruleNameService.findAll();
    }

    @Override
    protected RuleName getById(Integer id) {
        return ruleNameService.getById(id);
    }

    @Override
    protected RuleNameDto add(RuleNameCreateRequestDto dto) {
        return ruleNameService.add(dto);
    }

    @Override
    protected RuleNameDto update(Integer id, RuleNameUpdateRequestDto dto) {
        return ruleNameService.update(id, dto);
    }

    @Override
    protected void deleteById(Integer id) {
        ruleNameService.deleteById(id);
    }

    // méthodes spécifiques à l'entité ruleName => non généralistes : name unique.

    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("ruleName") RuleNameCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        log.debug("PostMapping/submitCreateForm,RuleNameCreateRequestDto="+dto);
        
        try {
            return create(dto, result, model);
        } catch (RuleNameAlreadyExistsException ex) {
            result.rejectValue("name", "error.user", ex.getMessage());
            return "ruleName/add";
        }
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("ruleName") RuleNameUpdateRequestDto dto,
                                   BindingResult result,
                                   Model model) {
        log.debug("PostMapping/submitUpdateForm,id="+id+",RuleNameUpdateRequestDto="+dto);
        
        try {
            return update(id, dto, result, model);
        } catch (RuleNameAlreadyExistsException ex) {
            result.rejectValue("name", "error.user", ex.getMessage());
            return "ruleName/update";
        }
        
    }
}

