package com.nnk.springboot.service.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidList.BidListCreateRequestDto;
import com.nnk.springboot.dto.bidList.BidListDto;
import com.nnk.springboot.dto.bidList.BidListUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface IBidListService extends ICrudService<BidList, BidListDto, BidListCreateRequestDto, BidListUpdateRequestDto> {}
 