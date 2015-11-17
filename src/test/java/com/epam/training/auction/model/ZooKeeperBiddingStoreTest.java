package com.epam.training.auction.model;

import com.epam.training.auction.common.BiddingRaceException;
import com.epam.training.auction.common.UserBidTransferObject;
import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction.exception.BiddingException;
import org.junit.Before;
import org.junit.BeforeClass;
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
    private static boolean setUpDone = false;
    @Autowired
    private BiddingStore biddingStore;
    @Autowired
    private UsersService usersService;
    private int auctionId;

    @Before
    public void before() {
        if (!setUpDone) {
            UserTransferObject userAlex = new UserTransferObject("Alex", "Alex");
            UserTransferObject userDmytro = new UserTransferObject("Dmytro", "Dmytro");
            UserTransferObject userAhmed = new UserTransferObject("Ahmed", "Ahmed");
            UserTransferObject userAdam = new UserTransferObject("Adam", "Adam");
            usersService.addUser(userAlex);
            usersService.addUser(userDmytro);
            usersService.addUser(userAhmed);
            usersService.addUser(userAdam);
            setUpDone = true;
        }
        auctionId = Math.abs(new Random().nextInt());
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