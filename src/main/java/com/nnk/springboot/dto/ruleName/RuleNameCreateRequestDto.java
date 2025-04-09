package com.nnk.springboot.dto.ruleName;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the ruleName creation form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleNameCreateRequestDto extends CommonRequestRuleNameDto{
 }
