package com.nnk.springboot.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the bidlist creation form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BidListCreateRequestDto extends CommonRequestBidListDto{
 }
