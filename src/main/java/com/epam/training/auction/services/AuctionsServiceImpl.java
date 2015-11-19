package com.epam.training.auction.services;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction.common.AuctionsService;
import com.epam.training.auction.entity.Auction;
import com.epam.training.auction.entity.User;
import com.epam.training.auction.exception.ItemNotFoundException;
import com.epam.training.auction.repository.AuctionRepository;
import com.epam.training.auction.repository.UserRepository;
import com.epam.training.auction.util.AuctionMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Service(value = "auctionsServiceImpl")
public final class AuctionsServiceImpl implements AuctionsService {
    private static final Logger LOGGER = Logger.getLogger(AuctionsServiceImpl.class);
    private static final String AUCTION_NOT_FOUND_MESSAGE = "Auction with id %d was not found";

    private final UserRepository userRepository;

    private final AuctionRepository auctionRepository;

    @Autowired
    public AuctionsServiceImpl(UserRepository userRepository, AuctionRepository auctionRepository) {
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
    }

    public List<AuctionTransferObject> getAuctionsWithActive(boolean isActive) {
        List<Auction> auctions = isActive ? auctionRepository.findByActiveTrue() : auctionRepository.findByActiveFalse();
        return AuctionMapper.map(auctions);
    }

    public Long addAuction(AuctionTransferObject auctionTransferObject) {
        User seller = userRepository.findByUserName(auctionTransferObject.getSeller().getUsername());
        Auction auction = new Auction(auctionTransferObject.getTitle(), auctionTransferObject.getDescription(), seller);
        auction.setActive(true);
        auction = auctionRepository.save(auction);
        startAuctionTimer(auction);
        return auction.getId();
    }

    private void startAuctionTimer(Auction auction) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            LOGGER.info(String.format("The auction with id=%d is started", auction.getId()));
            waitTwoMinutes();
            archiveAuction(auction);
            LOGGER.info(String.format("The auction with id=%d is finished", auction.getId()));
        });
    }

    private void waitTwoMinutes() {
        try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void archiveAuction(Auction auction) {
        Auction finishedAuction = auctionRepository.getOne(auction.getId());
        finishedAuction.setActive(false);
        auctionRepository.save(finishedAuction);
    }

    public AuctionTransferObject getAuctionById(long id) {
        Auction auction = auctionRepository.findOne(id);

        if (auction == null) {
            throw new ItemNotFoundException(format(AUCTION_NOT_FOUND_MESSAGE, id));
        }

        return AuctionMapper.map(auction);
    }
}
