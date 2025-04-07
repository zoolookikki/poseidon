package com.nnk.springboot.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a bidlist modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BidListUpdateRequestDto extends CommonRequestBidListDto{

    private Integer id;
    
}
