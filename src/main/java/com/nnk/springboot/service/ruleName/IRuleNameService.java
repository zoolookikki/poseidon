package com.nnk.springboot.service.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameCreateRequestDto;
import com.nnk.springboot.dto.ruleName.RuleNameDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface IRuleNameService extends ICrudService<RuleName, RuleNameDto, RuleNameCreateRequestDto, RuleNameUpdateRequestDto> {}
 