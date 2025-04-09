package com.nnk.springboot.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * To display only the necessary fields.
 */
@Data
@AllArgsConstructor
public class TradeDto {
    
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;
    
}
