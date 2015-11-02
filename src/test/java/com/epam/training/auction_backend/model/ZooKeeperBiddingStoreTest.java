package com.epam.training.auction_backend.model;

import com.epam.training.auction.common.UserBidTransferObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Dmytro_Ulanovych on 10/30/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContextConfiguration.class)
public class ZooKeeperBiddingStoreTest {
    private static final int TEST_AUCTION_ID = 11;
    private static final int MAX_BID = 10000;
    @Autowired
    private BiddingStore biddingStore;

    @Test
    public void testGetMaxBid() throws Exception {
        biddingStore.makeBid(TEST_AUCTION_ID, 10, 2);
        biddingStore.makeBid(TEST_AUCTION_ID, 11, 4);
        biddingStore.makeBid(TEST_AUCTION_ID, 12, 1);
        biddingStore.makeBid(TEST_AUCTION_ID, MAX_BID, 2);

        UserBidTransferObject maxBid = biddingStore.getMaxBid(TEST_AUCTION_ID);

        assertThat(maxBid.getBid(), equalTo(MAX_BID));
    }

    @Test
    public void testMakeBid() throws Exception {

    }
}