package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListCreateRequestDto;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.dto.BidListUpdateRequestDto;

public interface IBidListService {
    List<BidListDto> findAll();
    BidList getById(Integer id);
    BidListDto addBidList(BidListCreateRequestDto dto);
    BidListDto updateBidList(Integer id, BidListUpdateRequestDto dto);
    void deleteById(Integer id);
}
