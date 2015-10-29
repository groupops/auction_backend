package com.epam.training.auction_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.epam.training.auction_backend.entity.Auction;

public class AuctionService {
	
	public static final Logger logger = Logger.getLogger(AuctionService.class);
	
	public List<Auction> getActiveAuctions() {
		return getAuctions(true);
	}

	public List<Auction> getArchivedAuctions() {
		return getAuctions(false);
	}

	public Optional<Auction> getAuctionById(long id) {
		Session session = SessionProvider.getSession();
		Optional<Auction> auction = Optional.of(session.load(Auction.class, id));
		session.close();
		return auction;
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
	
	private List<Auction> getAuctions(boolean status) {
		Session session = SessionProvider.getSession();
		Query query = session.createQuery("FROM Auction A WHERE A.IS_ACTIVE = :status");
		query.setParameter("status", status);
		List<Auction> auctions = query.list();
		session.close();
		return auctions;
	}
}
