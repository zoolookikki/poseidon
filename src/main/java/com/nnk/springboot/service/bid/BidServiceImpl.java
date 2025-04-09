package com.nnk.springboot.service.bid;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.bid.BidCreateRequestDto;
import com.nnk.springboot.dto.bid.BidDto;
import com.nnk.springboot.dto.bid.BidUpdateRequestDto;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BidServiceImpl
        extends AbstractCrudService<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto>
        implements IBidService {

    public BidServiceImpl(
            BidRepository bidRepository,
            BidMapper bidMapper) {
        super(bidRepository, bidMapper);
    }
    
}
