package com.nnk.springboot.dto.trade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a trade modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TradeUpdateRequestDto extends CommonRequestTradeDto{

    private Integer id;
    
}
