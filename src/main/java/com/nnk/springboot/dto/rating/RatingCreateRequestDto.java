package com.nnk.springboot.dto.rating;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the rating creation form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RatingCreateRequestDto extends CommonRequestRatingDto{
 }
