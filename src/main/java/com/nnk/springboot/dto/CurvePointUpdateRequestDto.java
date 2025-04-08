package com.nnk.springboot.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a curvpoint modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurvePointUpdateRequestDto extends CommonRequestCurvePointDto{

    private Integer id;
    
}
