package com.nnk.springboot.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.nnk.springboot.domain.Bid;

public class BidRepositoryIT extends AbstractRepositoryIT<Bid, Integer> {

    @Autowired
    private BidRepository bidRepository;

    @Override
    protected Bid createEntity() {
        return new Bid("Account Test", "Type Test", 10d);
    }
    
    @Override
    protected List<Bid> getMoreEntities() {
        return List.of(
            new Bid("Account 1", "Type 1", 10d),
            new Bid("Account 2", "Type 2", 20d)
        );
    }

    @Override
    protected JpaRepository<Bid, Integer> getRepository() {
        return bidRepository;
    }

    @Override
    protected Integer getId(Bid bid) {
        return bid.getId();
    }

    @Override
    protected void verifyCreate(Bid bid) {
        assertThat(bid.getAccount()).isEqualTo("Account Test");
        assertThat(bid.getType()).isEqualTo("Type Test");
        assertThat(bid.getBidQuantity()).isEqualTo(10d);
    }

    @Override
    protected void updateEntity(Bid bid) {
        bid.setAccount("Account updated Test");
        bid.setType("Type updated test");
        bid.setBidQuantity(20d);
    }

    @Override
    protected void verifyUpdate(Bid bid) {
        assertThat(bid.getAccount()).isEqualTo("Account updated Test");
        assertThat(bid.getType()).isEqualTo("Type updated test");
        assertThat(bid.getBidQuantity()).isEqualTo(20d);
    }
}
