package com.epam.training.auction.services;

import com.epam.training.auction.common.BiddingService;
import com.epam.training.auction.common.UserBidTransferObject;
import com.epam.training.auction.model.BiddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "biddingServiceImpl")
public class BiddingServiceImpl implements BiddingService {
    private final BiddingStore biddingStore;

    @Autowired
    public BiddingServiceImpl(BiddingStore biddingStore) {
        this.biddingStore = biddingStore;
    }

    @Override
    public UserBidTransferObject getMaxBid(long auctionId) {
        return biddingStore.getMaxBid(auctionId);
    }

    @Override
    public void bid(long auctionId, long bidPrice, long userId) {
        biddingStore.makeBid(auctionId, bidPrice, userId);
    }
}
