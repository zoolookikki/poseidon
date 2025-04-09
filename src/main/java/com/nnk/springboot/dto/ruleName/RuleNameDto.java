package com.nnk.springboot.dto.ruleName;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * To display only the necessary fields.
 */
@Data
@AllArgsConstructor
public class RuleNameDto {
    
    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
   
}
