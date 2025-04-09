package com.nnk.springboot.dto.rule;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the rule creation form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleCreateRequestDto extends CommonRequestRuleDto{
 }
