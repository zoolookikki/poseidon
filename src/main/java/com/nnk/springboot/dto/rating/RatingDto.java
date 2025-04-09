package com.nnk.springboot.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * To display only the necessary fields.
 */
@Data
@AllArgsConstructor
public class RatingDto {
    
    private Integer id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;
    
    
}
