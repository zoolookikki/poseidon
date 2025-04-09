package com.nnk.springboot.dto.rule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a rule modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleUpdateRequestDto extends CommonRequestRuleDto{

    private Integer id;
    
}
