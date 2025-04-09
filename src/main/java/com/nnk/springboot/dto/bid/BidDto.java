package com.nnk.springboot.dto.bid;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * To display only the necessary fields.
 */
@Data
@AllArgsConstructor
public class BidDto {
    
    private Integer id;
    private String account;
    private String type;
    private Double bidQuantity;
    
}
