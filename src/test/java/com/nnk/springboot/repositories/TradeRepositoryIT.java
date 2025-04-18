package com.nnk.springboot.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.nnk.springboot.domain.Trade;

public class TradeRepositoryIT extends AbstractRepositoryIT<Trade, Integer> {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    protected Trade createEntity() {
        return new Trade("Trade Account", "Type", 0.01d);
    }
    
    @Override
    protected List<Trade> getMoreEntities() {
        return List.of(
            new Trade("Account 1", "Type 1", 1.23d),
            new Trade("Account 2", "Type 2", 2.22d),
            new Trade("Account 3", "Type 3", 3.12d),
            new Trade("Account 4", "Type 4", 0.04d)
        );
    }

    @Override
    protected JpaRepository<Trade, Integer> getRepository() {
        return tradeRepository;
    }

    @Override
    protected Integer getId(Trade trade) {
        return trade.getId();
    }

    @Override
    protected void verifyCreate(Trade trade) {
        assertThat(trade.getAccount()).isEqualTo("Trade Account");
        assertThat(trade.getType()).isEqualTo("Type");
        assertThat(trade.getBuyQuantity()).isEqualTo(0.01);
    }

    @Override
    protected void updateEntity(Trade trade) {
        trade.setAccount("Trade Account updated");
        trade.setType("Type updated");
        trade.setBuyQuantity(3.21d);
    }

    @Override
    protected void verifyUpdate(Trade trade) {
        assertThat(trade.getAccount()).isEqualTo("Trade Account updated");
        assertThat(trade.getType()).isEqualTo("Type updated");
        assertThat(trade.getBuyQuantity()).isEqualTo(3.21d);
    }
}
