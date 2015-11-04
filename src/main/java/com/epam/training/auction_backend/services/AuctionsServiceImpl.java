package com.epam.training.auction_backend.services;

import java.util.List;
import java.util.Optional;
import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction_backend.exception.ItemNotFoundException;
import com.epam.training.auction_backend.util.AuctionMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction.common.AuctionsService;
import com.epam.training.auction_backend.entity.Auction;
import com.epam.training.auction_backend.entity.User;

import static java.lang.String.format;

@Service(value = "auctionsServiceImpl")
public final class AuctionsServiceImpl implements AuctionsService {
    private static final Logger LOGGER = Logger.getLogger(AuctionsServiceImpl.class);
    private static final String AUCTION_NOT_FOUND_MESSAGE = "Auction with id %d was not found";

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

    public void addAuction(AuctionTransferObject auctionTransferObject) {
        User seller = new User(auctionTransferObject.getSeller().getUsername(), auctionTransferObject.getSeller().getPassword());
        Auction auction = new Auction(auctionTransferObject.getTitle(), auctionTransferObject.getDescription(), seller);
        auctionRepository.save(auction);
        LOGGER.info("Book " + auction.getTitle() + " is successfully added to the auction.");
    }

    public Optional<AuctionTransferObject> getAuctionById(long id) {
        Auction auction = auctionRepository.findOne(id);
        Optional<AuctionTransferObject> auctionTransferObject;

        if (auction == null) {
            LOGGER.error(format(AUCTION_NOT_FOUND_MESSAGE, id));
            throw new ItemNotFoundException(format(AUCTION_NOT_FOUND_MESSAGE, id));
        } else {
            auctionTransferObject = Optional.of(AuctionMapper.map(auction));
        }

        return auctionTransferObject;
    }
}
