package com.epam.training.auction_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.epam.training.auction_backend.entity.Auction;

public class AuctionController {

	public static final Logger logger = Logger.getLogger(AuctionController.class);
	
	public List<Auction> getActiveAuctions() {
		Session session = SessionProvider.getSession();
		List<Auction> auctions = session.createQuery("FROM Auction A WHERE A.IS_ACTIVE=true").list();
		session.close();
		return auctions;
	}

	public List<Auction> getArchivedAuctions() {
		return null;

	}

	public Optional<Auction> getAuctionsAuctionById(long id) {
		return null;

	}

	public long addAuction(String title, String description, long sellerUserId) {
		Session session = SessionProvider.getSession();
		Auction auction = new Auction(title, description, sellerUserId);
		session.beginTransaction();
		session.save(auction);
		session.getTransaction().commit();
		session.close();
		logger.info("Book " + auction.getTitle() + " is successfully added to the auction.");
		return auction.getId();
	}
}
