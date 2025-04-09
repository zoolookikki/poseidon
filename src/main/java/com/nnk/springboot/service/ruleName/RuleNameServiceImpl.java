package com.nnk.springboot.service.ruleName;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameCreateRequestDto;
import com.nnk.springboot.dto.ruleName.RuleNameDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateRequestDto;
import com.nnk.springboot.exception.RuleNameAlreadyExistsException;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RuleNameServiceImpl
        extends AbstractCrudService<RuleName, RuleNameDto, RuleNameCreateRequestDto, RuleNameUpdateRequestDto>
        implements IRuleNameService {

    private final RuleNameRepository ruleNameRepository;
    
    public RuleNameServiceImpl(
            RuleNameRepository ruleNameRepository,
            RuleNameMapper ruleNameMapper) {
        super(ruleNameRepository, ruleNameMapper);
        this.ruleNameRepository = ruleNameRepository;
    }
    
    /*
    add spécifique pour RuleName car :
        - contrôle sur le name si il existe déjà.
    */
    @Override
    public RuleNameDto add(RuleNameCreateRequestDto dto) {
        // vérification que le name est unique.
        if (ruleNameRepository.findByName(dto.getName()).isPresent()) {
            throw new RuleNameAlreadyExistsException("The name '" + dto.getName() + "' is already in use");
        }

        return super.add(dto);
    }
    
    /*
    update spécifique pour RuleName car :
         - contrôle sur le name si il existe déjà.
    */
    @Override
    public RuleNameDto update(Integer id, RuleNameUpdateRequestDto dto) {
        Optional<RuleName> sameName = ruleNameRepository.findByName(dto.getName());

        if (sameName.isPresent() && !sameName.get().getId().equals(id)) {
            throw new RuleNameAlreadyExistsException("The name '" + dto.getName() + "' is already in use");
        }

        return super.update(id, dto);
    }    
    
}
