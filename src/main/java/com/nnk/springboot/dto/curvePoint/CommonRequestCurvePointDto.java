package com.nnk.springboot.dto.curvePoint;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public abstract class CommonRequestCurvePointDto {

    @NotNull(message = "This field is required")
    // strictement > 0
    @Positive(message = "Must be a positive number")
    @Max(value = 255, message = "Must not exceed 255")
    private Integer curveId;
    
    @NotNull(message = "This field is required")
    // 0.10 = 1 mois, 1.00 = 1 année.
    @DecimalMin(value = "0.10", message = "Must be at least 0.10")
    private Double term;
    
    @NotNull(message = "This field is required")
    @DecimalMin(value = "0.01", message = "Must be at least 0.01")
    private Double valuePoint;
    
}
