package com.nnk.springboot.dto.bidList;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * To display only the necessary fields.
 */
@Data
@AllArgsConstructor
public class BidListDto {
    
    private Integer id;
    private String account;
    private String type;
    private Double bidQuantity;
    
}
