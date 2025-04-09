package com.nnk.springboot.dto.trade;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestTradeDto {

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum length: 30 characters")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum length: 30 characters")
    private String type;

    @NotNull(message = "BuyQuantity is required")
    @DecimalMin(value = "0.01", message = "BuyQuantity must be at least 0.01")
    private Double buyQuantity;
    
    
}
