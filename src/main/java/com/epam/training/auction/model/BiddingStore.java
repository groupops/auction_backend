package com.epam.training.auction.model;

import com.epam.training.auction.common.BiddingRaceException;
import com.epam.training.auction.common.UserBidTransferObject;
import com.epam.training.auction.exception.BiddingException;

/**
 * Created by Dmytro_Ulanovych on 10/29/2015.
 */
public interface BiddingStore {

    UserBidTransferObject getMaxBid(long auctionId) throws BiddingException;

    boolean makeBid(long auctionId, long amount, long userId) throws BiddingRaceException;

}
