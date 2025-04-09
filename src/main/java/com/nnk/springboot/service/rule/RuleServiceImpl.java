package com.nnk.springboot.service.rule;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.rule.RuleCreateRequestDto;
import com.nnk.springboot.dto.rule.RuleDto;
import com.nnk.springboot.dto.rule.RuleUpdateRequestDto;
import com.nnk.springboot.exception.RulenameAlreadyExistsException;
import com.nnk.springboot.mapper.RuleMapper;
import com.nnk.springboot.repositories.RuleRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RuleServiceImpl
        extends AbstractCrudService<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto>
        implements IRuleService {

    private final RuleRepository ruleRepository;
    
    public RuleServiceImpl(
            RuleRepository ruleRepository,
            RuleMapper ruleMapper) {
        super(ruleRepository, ruleMapper);
        this.ruleRepository = ruleRepository;
    }
    
    /*
    add spécifique pour Rule car :
        - contrôle sur le name si il existe déjà.
    */
    @Override
    public RuleDto add(RuleCreateRequestDto dto) {
        // vérification que le name est unique.
        if (ruleRepository.findByName(dto.getName()).isPresent()) {
            throw new RulenameAlreadyExistsException("The name '" + dto.getName() + "' is already in use");
        }

        return super.add(dto);
    }
    
    /*
    update spécifique pour Rule car :
         - contrôle sur le name si il existe déjà.
    */
    @Override
    public RuleDto update(Integer id, RuleUpdateRequestDto dto) {
        Optional<Rule> sameName = ruleRepository.findByName(dto.getName());

        if (sameName.isPresent() && !sameName.get().getId().equals(id)) {
            throw new RulenameAlreadyExistsException("The name '" + dto.getName() + "' is already in use");
        }

        return super.update(id, dto);
    }    
    
}
