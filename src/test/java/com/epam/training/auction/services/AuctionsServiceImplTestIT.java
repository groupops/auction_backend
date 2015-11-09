package com.epam.training.auction.services;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction.entity.Auction;
import com.epam.training.auction.entity.User;
import com.epam.training.auction.repository.AuctionRepository;
import com.epam.training.auction.repository.UserRepository;
import com.epam.training.auction.util.CommonIntegrationTestSuit;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuctionsServiceImplTestIT extends CommonIntegrationTestSuit {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersService usersService;

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
        final int actualArchivedAuctionsSize = archivedAuctions.size();
        assertEquals("The getArchivedAuctions() method fetched wrong size of auctions",
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
        final int actualActiveAuctionsSize = activeAuctions.size();
        assertEquals("The getActiveAuctions() method fetched wrong size of auctions",
                expectedActiveAuctionsSize, actualActiveAuctionsSize);
    }

    @Test
    @Transactional
    public void getAuctionById() {
        // create seller for auctions
        User seller = createUser("Seller", "Seller");

        // create active auction that must be saved in DB and get ID of saved entity
        final long expectedAuctionId = auctionRepository.save(createAuction(true, seller)).getId();

        // get the ID of fetched object
        final long actualAuctionId = auctionsService.getAuctionById(expectedAuctionId).get().getId();

        assertEquals("The getAuctionById method fetched wrong object", expectedAuctionId, actualAuctionId);
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
