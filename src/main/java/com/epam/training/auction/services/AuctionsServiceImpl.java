package com.epam.training.auction.services;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction.common.AuctionsService;
import com.epam.training.auction.entity.Auction;
import com.epam.training.auction.entity.User;
import com.epam.training.auction.exception.ItemNotFoundException;
import com.epam.training.auction.repository.AuctionRepository;
import com.epam.training.auction.util.AuctionMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Long addAuction(AuctionTransferObject auctionTransferObject) {
        User seller = new User(auctionTransferObject.getSeller().getUsername(), auctionTransferObject.getSeller().getPassword());
        Auction auction = new Auction(auctionTransferObject.getTitle(), auctionTransferObject.getDescription(), seller);
        auction = auctionRepository.save(auction);
        return auction.getId();
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
