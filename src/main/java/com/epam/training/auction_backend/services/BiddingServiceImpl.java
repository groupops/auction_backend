package com.epam.training.auction_backend.services;

import com.epam.training.auction.common.BiddingService;
import com.epam.training.auction.common.UserBidTransferObject;
import com.epam.training.auction_backend.model.BiddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "biddingServiceImpl")
public class BiddingServiceImpl implements BiddingService {
    private BiddingStore biddingStore;

    @Autowired
    public BiddingServiceImpl(BiddingStore biddingStore) {
        this.biddingStore = biddingStore;
    }

    @Override
    public Optional<UserBidTransferObject> getMaxBid(long auctionId) {
        return Optional.of(biddingStore.getMaxBid(auctionId));
    }

    @Override
    public void bid(long auctionId, long bidPrice, long userId) {
        biddingStore.makeBid(auctionId, bidPrice, userId);
    }
}
