package com.nnk.springboot.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestRatingDto {

    @NotBlank(message = "This field is required")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String moodysRating;

    @NotBlank(message = "This field is required")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String sandPRating;

    @NotBlank(message = "This field is required")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String fitchRating;

    @NotNull(message = "This field is required")
    // strictement > 0
    @Positive(message = "Must be a positive number")
    @Max(value = 255, message = "Must not exceed 255")
    private Integer orderNumber;
        
}
