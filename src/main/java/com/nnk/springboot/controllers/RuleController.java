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

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.rule.RuleCreateRequestDto;
import com.nnk.springboot.dto.rule.RuleDto;
import com.nnk.springboot.dto.rule.RuleUpdateRequestDto;
import com.nnk.springboot.exception.RulenameAlreadyExistsException;
import com.nnk.springboot.mapper.RuleMapper;
import com.nnk.springboot.service.rule.IRuleService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/rule")
@Log4j2
public class RuleController extends AbstractCrudController<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto> {

    private final IRuleService ruleService;
    private final RuleMapper ruleMapper;

    @Autowired
    public RuleController(IRuleService ruleService, RuleMapper ruleMapper) {
        this.ruleService = ruleService;
        this.ruleMapper = ruleMapper;
    }

    @Override
    protected String getEntityName() {
        return "rule";
    }

    @Override
    protected RuleCreateRequestDto emptyCreateDto() {
        return new RuleCreateRequestDto();
    }

    @Override
    protected RuleUpdateRequestDto getUpdateDto(Rule entity) {
        return ruleMapper.toUpdateRequestDto(entity);
    }

    @Override
    protected List<RuleDto> findAll() {
        return ruleService.findAll();
    }

    @Override
    protected Rule getById(Integer id) {
        return ruleService.getById(id);
    }

    @Override
    protected RuleDto add(RuleCreateRequestDto dto) {
        return ruleService.add(dto);
    }

    @Override
    protected RuleDto update(Integer id, RuleUpdateRequestDto dto) {
        return ruleService.update(id, dto);
    }

    @Override
    protected void deleteById(Integer id) {
        ruleService.deleteById(id);
    }

    // méthodes spécifiques à l'entité rule => non généralistes : name unique.

    @PostMapping("/validate")
    public String submitCreateForm(@Valid @ModelAttribute("rule") RuleCreateRequestDto dto,
                                   BindingResult result, 
                                   Model model) {
        log.debug("PostMapping/submitCreateForm,RuleCreateRequestDto="+dto);
        
        try {
            return create(dto, result, model);
        } catch (RulenameAlreadyExistsException ex) {
            result.rejectValue("name", "error.user", ex.getMessage());
            return "rule/add";
        }
    }

    @PostMapping("/update/{id}")
    public String submitUpdateForm(@PathVariable Integer id,
                                   @Valid @ModelAttribute("rule") RuleUpdateRequestDto dto,
                                   BindingResult result,
                                   Model model) {
        log.debug("PostMapping/submitUpdateForm,id="+id+",RuleUpdateRequestDto="+dto);
        
        try {
            return update(id, dto, result, model);
        } catch (RulenameAlreadyExistsException ex) {
            result.rejectValue("name", "error.user", ex.getMessage());
            return "rule/update";
        }
        
    }
}

