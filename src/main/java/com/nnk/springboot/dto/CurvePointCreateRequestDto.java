package com.nnk.springboot.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the curvepoint creation form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurvePointCreateRequestDto extends CommonRequestCurvePointDto{
 }
