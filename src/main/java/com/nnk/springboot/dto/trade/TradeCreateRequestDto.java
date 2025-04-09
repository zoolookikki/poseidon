package com.nnk.springboot.dto.trade;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the trade creation form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TradeCreateRequestDto extends CommonRequestTradeDto{
 }
