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

    @Autowired
    private UserRepository userRepository;

    private AuctionRepository auctionRepository;

    @Autowired
    public AuctionsServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public List<AuctionTransferObject> getActiveAuctions() {
        List<Auction> auctions = auctionRepository.findByActiveTrue();
        return AuctionMapper.map(auctions);
    }

    public List<AuctionTransferObject> getArchivedAuctions() {
        List<Auction> auctions = auctionRepository.findByActiveFalse();
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
            LOGGER.info("The auction is started");
            try {
                TimeUnit.MINUTES.sleep(2);
                archiveAuction(auction);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("The auction is finished");
        });
    }

    private void archiveAuction(Auction auction){
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
