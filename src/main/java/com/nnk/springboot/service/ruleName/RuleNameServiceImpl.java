package com.nnk.springboot.service.ruleName;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameCreateRequestDto;
import com.nnk.springboot.dto.ruleName.RuleNameDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateRequestDto;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RuleNameServiceImpl
        extends AbstractCrudService<RuleName, RuleNameDto, RuleNameCreateRequestDto, RuleNameUpdateRequestDto>
        implements IRuleNameService {

    public RuleNameServiceImpl(
            RuleNameRepository ruleNameRepository,
            RuleNameMapper ruleNameMapper) {
        super(ruleNameRepository, ruleNameMapper);
    }
    
}
