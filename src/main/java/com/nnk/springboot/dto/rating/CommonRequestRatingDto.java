package com.nnk.springboot.dto.rating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestRatingDto {

    @NotBlank(message = "MoodysRating is mandatory")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    @Size(max = 125, message = "Maximum length: 125 characters")
    private String fitchRating;

    @NotNull(message = "OrderNumber is required")
    // strictement > 0
    @Positive(message = "OrderNumber must be a positive number")
    private Integer orderNumber;
        
}
