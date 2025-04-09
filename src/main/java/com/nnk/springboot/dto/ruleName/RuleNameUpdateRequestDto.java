package com.nnk.springboot.dto.ruleName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a ruleName modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleNameUpdateRequestDto extends CommonRequestRuleNameDto{

    private Integer id;
    
}
