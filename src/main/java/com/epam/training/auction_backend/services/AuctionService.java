package com.epam.training.auction_backend.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction.common.AuctionsService;
import com.epam.training.auction_backend.entity.Auction;
import com.epam.training.auction_backend.entity.User;

public final class AuctionService implements AuctionsService {
	
	private static final Logger logger = Logger.getLogger(AuctionService.class);
	
	@Autowired
	private AuctionRepository repository;
	
	public List<AuctionTransferObject> getActiveAuctions() {
		return repository.findByIsActive(true);
	}

	public List<AuctionTransferObject> getArchivedAuctions() {
		return repository.findByIsActive(false);
	}

	public void addAuction(AuctionTransferObject auctionTransferObject) {
		User seller = new User(auctionTransferObject.getSeller().getUsername(), auctionTransferObject.getSeller().getPassword());
		Auction auction = new Auction(auctionTransferObject.getTitle(), auctionTransferObject.getDescription(), seller);
		repository.save(auction);
		logger.info("Book " + auction.getTitle() + " is successfully added to the auction.");
	}

	public Optional<AuctionTransferObject> getAuctionById(long id) {
		return repository.findOne(id);
	}
}
