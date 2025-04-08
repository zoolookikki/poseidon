package com.nnk.springboot.dto.curvePoint;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * To display only the necessary fields.
 */
@Data
@AllArgsConstructor
public class CurvePointDto {
    
    private Integer id;
    private Integer curveId;
    private Double term;
    private Double value;
    
}
