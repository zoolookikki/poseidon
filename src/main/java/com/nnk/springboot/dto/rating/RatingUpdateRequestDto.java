package com.nnk.springboot.dto.rating;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a rating modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RatingUpdateRequestDto extends CommonRequestRatingDto{

    private Integer id;
    
}
