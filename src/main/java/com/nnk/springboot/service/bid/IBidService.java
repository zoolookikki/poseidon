package com.nnk.springboot.service.bid;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.bid.BidCreateRequestDto;
import com.nnk.springboot.dto.bid.BidDto;
import com.nnk.springboot.dto.bid.BidUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface IBidService extends ICrudService<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto> {}
 