package com.nnk.springboot.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeCreateRequestDto;
import com.nnk.springboot.dto.trade.TradeDto;
import com.nnk.springboot.dto.trade.TradeUpdateRequestDto;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.mapper.IMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.trade.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest extends AbstractCrudServiceTest<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto> {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeMapper tradeMapper;

    private Trade trade1, trade2;
    private TradeDto tradeDto1, tradeDto2;
    private TradeCreateRequestDto tradeCreateDto1;
    private TradeUpdateRequestDto tradeUpdateDto1;
    private Trade tradeUpdated1;
    private TradeDto tradeDtoUpdated1;

    @BeforeEach
    void setUp() {
        trade1 = new Trade(1,"Account 1", "Type 1", 1.23d);
        trade2 = new Trade(2,"Account 2", "Type 2", 2.22d);
        tradeDto1 = new TradeDto(1,"Account 1", "Type 1", 1.23d);
        tradeDto2 = new TradeDto(2, "Account 2", "Type 2", 2.22d);
        tradeCreateDto1 = new TradeCreateRequestDto();
        tradeCreateDto1.setAccount(trade1.getAccount());
        tradeCreateDto1.setType(trade1.getType());
        tradeCreateDto1.setBuyQuantity(trade1.getBuyQuantity());
        tradeUpdateDto1 = new TradeUpdateRequestDto();
        tradeUpdateDto1.setAccount(trade1.getAccount() + " updated");
        tradeUpdateDto1.setType(trade1.getType() + " updated");
        tradeUpdateDto1.setBuyQuantity(3.21d);
        tradeUpdated1 = new Trade(1,"Account 1 updated", "Type 1 updated", 3.21); 
        tradeDtoUpdated1 = new TradeDto(1, "Account 1 updated", "Type 1 updated", 3.21); 
    }

    @Override
    protected Trade getEntity() {
        return trade1;
    }
    
    @Override
    protected List<Trade> getEntities() {
        return List.of(trade1, trade2);
    }

    @Override
    protected List<TradeDto> getDtos() {
        return List.of(tradeDto1, tradeDto2);
    }

    @Override
    protected AbstractCrudService<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto> getService() {
        return tradeService;
    }

    @Override
    protected JpaRepository<Trade, Integer> getRepository() {
        return tradeRepository;
    }

    @Override
    protected IMapper<Trade, TradeDto, TradeCreateRequestDto, TradeUpdateRequestDto> getMapper() {
        return tradeMapper;
    }

    @Override
    protected TradeCreateRequestDto getCreateDto() {
        return tradeCreateDto1;
    }
    
    @Override
    protected TradeDto getExpectedCreatedDto() {
        return tradeDto1;
    }
    
    @Override
    protected TradeUpdateRequestDto getUpdateDto() {
        return tradeUpdateDto1;
    }
    @Override
    protected Trade getUpdatedEntity() {
        return tradeUpdated1;
    }

    @Override
    protected TradeDto getExpectedUpdatedDto() {
        return tradeDtoUpdated1;
    }
}
