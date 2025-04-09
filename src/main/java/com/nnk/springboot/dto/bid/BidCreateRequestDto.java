package com.nnk.springboot.dto.bid;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the bidlist creation form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BidCreateRequestDto extends CommonRequestBidDto{
 }
