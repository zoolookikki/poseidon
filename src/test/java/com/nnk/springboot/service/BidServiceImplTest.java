package com.nnk.springboot.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.bid.BidCreateRequestDto;
import com.nnk.springboot.dto.bid.BidDto;
import com.nnk.springboot.dto.bid.BidUpdateRequestDto;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.mapper.IMapper;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.service.bid.BidServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BidServiceImplTest extends AbstractCrudServiceTest<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto> {

    @InjectMocks
    private BidServiceImpl bidService;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private BidMapper bidMapper;

    private Bid bid1, bid2;
    private BidDto bidDto1, bidDto2;
    private BidCreateRequestDto bidCreateDto1;
    private BidUpdateRequestDto bidUpdateDto1;
    private Bid bidUpdated1;
    private BidDto bidDtoUpdated1;

    
    // ici, il faut juste implémenter ce jeu d'essai pour chaque entité.
    @BeforeEach
    void setUp() {
        bid1 = new Bid(1, "Account 1", "Type 1", 10d);
        bid2 = new Bid(2, "Account 2", "Type 2", 20d);
        bidDto1 = new BidDto(1, "Account 1", "Type 1", 10d);
        bidDto2 = new BidDto(2, "Account 2", "Type 2", 20d);
        bidCreateDto1 = new BidCreateRequestDto();
        bidCreateDto1.setAccount("Account 1");
        bidCreateDto1.setType("Type 1");
        bidCreateDto1.setBidQuantity(10d);
        bidUpdateDto1 = new BidUpdateRequestDto();
        bidUpdateDto1.setAccount("Account 1 updated");
        bidUpdateDto1.setType("Type 1 updated");
        bidUpdateDto1.setBidQuantity(11d);
        bidUpdated1 = new Bid(1, "Account 1 updated", "Type 1 updated", 11d);
        bidDtoUpdated1 = new BidDto(1, "Account 1 updated", "Type 1 updated", 11d);
    }

    @Override
    protected Bid getEntity() {
        return bid1;
    }
    
    @Override
    protected List<Bid> getEntities() {
        return List.of(bid1, bid2);
    }

    @Override
    protected List<BidDto> getDtos() {
        return List.of(bidDto1, bidDto2);
    }

    @Override
    protected AbstractCrudService<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto> getService() {
        return bidService;
    }

    @Override
    protected JpaRepository<Bid, Integer> getRepository() {
        return bidRepository;
    }

    @Override
    protected IMapper<Bid, BidDto, BidCreateRequestDto, BidUpdateRequestDto> getMapper() {
        return bidMapper;
    }
    
    @Override
    protected BidCreateRequestDto getCreateDto() {
        return bidCreateDto1;
    }
    
    @Override
    protected BidDto getExpectedCreatedDto() {
        return bidDto1;
    }

    @Override
    protected BidUpdateRequestDto getUpdateDto() {
        return bidUpdateDto1;
    }
    @Override
    protected Bid getUpdatedEntity() {
        return bidUpdated1;
    }

    @Override
    protected BidDto getExpectedUpdatedDto() {
        return bidDtoUpdated1;
    }
}
