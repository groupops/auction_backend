package com.epam.training.auction_backend.services;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction_backend.entity.Auction;
import com.epam.training.auction_backend.entity.User;
import com.epam.training.auction_backend.model.TestContextConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContextConfiguration.class)
public class AuctionsServiceImplTestIT {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    private AuctionsServiceImpl auctionsService;

    @Before
    public void setUp() {
        auctionsService = new AuctionsServiceImpl(auctionRepository);
    }

    @Test
    @Transactional
    public void getArchivedAuctions() {
        final int expectedArchivedAuctionsSize = 2;

        // create seller and winner for auctions
        User seller = createUser("Seller", "Seller");
        User winner = createUser("Winner", "Winner");

        // create two auctions that must be saved in DB
        auctionRepository.save(createAuction(false, seller, winner));
        auctionRepository.save(createAuction(false, seller, winner));

        // get archived auctions and check if size match
        List<AuctionTransferObject> archivedAuctions = auctionsService.getArchivedAuctions();
        int actualArchivedAuctionsSize = archivedAuctions.size();
        Assert.assertEquals("The getArchivedAuctions() method fetched wrong size of auctions",
                expectedArchivedAuctionsSize, actualArchivedAuctionsSize);
    }

    @Test
    @Transactional
    public void getActiveAuctions() {
        final int expectedActiveAuctionsSize = 2;

        // create seller for auctions
        User seller = createUser("Seller", "Seller");

        // create two active auctions that must be saved in DB
        auctionRepository.save(createAuction(true, seller));
        auctionRepository.save(createAuction(true, seller));

        // get archived auctions and check if size match
        List<AuctionTransferObject> activeAuctions = auctionsService.getActiveAuctions();
        int actualActiveAuctionsSize = activeAuctions.size();
        Assert.assertEquals("The getActiveAuctions() method fetched wrong size of auctions",
                expectedActiveAuctionsSize, actualActiveAuctionsSize);
    }

    private User createUser(String name, String password) {
        return new User(name, password);
    }

    private Auction createAuction(boolean isActive, User seller, User winner) {
        Auction auction = new Auction();
        auction.setActive(isActive);
        auction.setSellerUser(userRepository.save(seller));
        auction.setWinnerUser(userRepository.save(winner));
        return auction;
    }

    private Auction createAuction(boolean isActive, User seller) {
        Auction auction = new Auction();
        auction.setActive(isActive);
        auction.setSellerUser(userRepository.save(seller));
        return auction;
    }
}
