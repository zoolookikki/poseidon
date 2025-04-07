package com.nnk.springboot.service;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListCreateRequestDto;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.dto.BidListUpdateRequestDto;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BidListServiceImpl extends AbstractCrudService<BidList, BidListDto> implements IBidListService {

    private final BidListRepository bidListRepository;
    private final BidListMapper bidListMapper;

    public BidListServiceImpl(BidListRepository bidListRepository,
                              BidListMapper bidListMapper) {
        super(bidListRepository);
        this.bidListRepository = bidListRepository;
        this.bidListMapper = bidListMapper;
    }

    @Override
    protected BidListDto toDto(BidList bidList) {
        return bidListMapper.toDto(bidList);
    }

    @Override
    public BidListDto addBidList(BidListCreateRequestDto dto) {
        BidList bidlist = bidListMapper.fromCreateRequestDto(dto);
        return bidListMapper.toDto(bidListRepository.save(bidlist));
    }

    @Override
    public BidListDto updateBidList(Integer id, BidListUpdateRequestDto dto) {
        BidList bidlist = getById(id);
        // pour être sûr de ce que l'on modifie.
        bidlist.setAccount(dto.getAccount());
        bidlist.setType(dto.getType());
        bidlist.setBidQuantity(dto.getBidQuantity());
        return bidListMapper.toDto(bidListRepository.save(bidlist));
    }
}
