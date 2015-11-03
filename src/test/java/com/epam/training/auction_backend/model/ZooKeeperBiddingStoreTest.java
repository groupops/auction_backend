package com.epam.training.auction_backend.model;

import com.epam.training.auction.common.BiddingRaceException;
import com.epam.training.auction.common.UserBidTransferObject;
import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction_backend.exception.BiddingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Dmytro_Ulanovych on 10/30/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContextConfiguration.class)
public class ZooKeeperBiddingStoreTest {
    private static final long MAX_BID = 10000;
    @Autowired
    private BiddingStore biddingStore;
    @Autowired
    private UsersService usersService;
    private int auctionId;

    @Before
    public void before() {
    	UserTransferObject userAlex = new UserTransferObject(1, "Alex", "Alex");
    	UserTransferObject userDmytro = new UserTransferObject(2, "Dmytro", "Dmytro");
    	UserTransferObject userAhmed = new UserTransferObject(3, "Ahmed", "Ahmed");
    	UserTransferObject userAdam = new UserTransferObject(4, "Adam", "Adam");
    	usersService.addUser(userAlex);
    	usersService.addUser(userDmytro);
    	usersService.addUser(userAhmed);
    	usersService.addUser(userAdam);
    	
    	auctionId = new Random().nextInt();
    }
    
    @Test
    public void testGetMaxBid() throws Exception {
    	biddingStore.makeBid(auctionId, 10, 2);
        biddingStore.makeBid(auctionId, 11, 4);
        biddingStore.makeBid(auctionId, 12, 1);
        biddingStore.makeBid(auctionId, MAX_BID, 2);

        UserBidTransferObject maxBid = biddingStore.getMaxBid(auctionId);

        assertThat(maxBid.getBid(), equalTo(MAX_BID));
    }

    @Test(expected = BiddingException.class)
    public void exceptionWhenAskForMaximumValueBeforeBidingStarted() throws Exception {
    	biddingStore.getMaxBid(auctionId);
    }

    @Test(expected = BiddingRaceException.class)
    public void exceptionWhenTwoBidsWithSamePrice() throws Exception {
    	biddingStore.makeBid(auctionId, 123, 2);
        biddingStore.makeBid(auctionId, 124, 2);
        biddingStore.makeBid(auctionId, 125, 2);
        biddingStore.makeBid(auctionId, 125, 2);
    }
}