package com.nnk.springboot.dto.bidList;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestBidListDto {

    @NotBlank(message = "This field is required")
    @Size(max = 30, message = "Maximum length: 30 characters")
    private String account;

    @NotBlank(message = "This field is required")
    @Size(max = 30, message = "Maximum length: 30 characters")
    private String type;

    @NotNull(message = "This field is required")
    @DecimalMin(value = "0.01", message = "Must be at least 0.01")
    private Double bidQuantity;
    
}
