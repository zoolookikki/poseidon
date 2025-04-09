package com.nnk.springboot.dto.ruleName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestRuleNameDto {

    @NotBlank(message = "This field is required")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String name;

    @NotBlank(message = "This field is required")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String description;

    @Size(max = 125, message = "Maximum length: 125 characters")
    private String json;

    @Size(max = 512, message = "Maximum length: 512 characters")
    private String template;

    @Size(max = 125, message = "Maximum length: 125 characters")
    private String sqlStr;

    @Size(max = 125, message = "Maximum length: 125 characters")
    private String sqlPart;
    
}
