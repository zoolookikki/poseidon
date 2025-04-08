package com.nnk.springboot.service.bidList;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidList.BidListCreateRequestDto;
import com.nnk.springboot.dto.bidList.BidListDto;
import com.nnk.springboot.dto.bidList.BidListUpdateRequestDto;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BidListServiceImpl
        extends AbstractCrudService<BidList, BidListDto, BidListCreateRequestDto, BidListUpdateRequestDto>
        implements IBidListService {

    public BidListServiceImpl(
            BidListRepository bidListRepository,
            BidListMapper bidListMapper) {
        super(bidListRepository, bidListMapper);
    }
    
}
