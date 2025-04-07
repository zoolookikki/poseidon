package com.nnk.springboot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestBidListDto {

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Account must not exceed 30 characters")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Type must not exceed 30 characters")
    private String type;

    @NotNull(message = "Bid Quantity is required")
    @DecimalMin(value = "0.01", message = "Bid Quantity must be at least 0.01")
    private Double bidQuantity;
    
}
