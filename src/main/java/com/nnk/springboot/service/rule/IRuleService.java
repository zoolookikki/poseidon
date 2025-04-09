package com.nnk.springboot.service.rule;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.rule.RuleCreateRequestDto;
import com.nnk.springboot.dto.rule.RuleDto;
import com.nnk.springboot.dto.rule.RuleUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface IRuleService extends ICrudService<Rule, RuleDto, RuleCreateRequestDto, RuleUpdateRequestDto> {}
 