package com.nnk.springboot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public abstract class CommonRequestCurvePointDto {

    @NotNull(message = "CurveId is required")
    // strictement > 0
    @Positive(message = "CurveId must be a positive number")
    private Integer curveId;
    
    @NotNull(message = "Term is required")
    // 0.10 = 1 mois, 1.00 = 1 année.
    @DecimalMin(value = "0.10", message = "Term must be at least 0.10")
    private Double term;
    
    @NotNull(message = "Value is required")
    @DecimalMin(value = "0.01", message = "Value must be at least 0.01")
    private Double value;
    
}
