package com.nnk.springboot.dto.bid;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a bidlist modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BidUpdateRequestDto extends CommonRequestBidDto{

    private Integer id;
    
}
