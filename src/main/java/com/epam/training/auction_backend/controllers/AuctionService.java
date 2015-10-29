package com.epam.training.auction_backend.controllers;

import java.util.ArrayList;
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
		Optional<Auction> auction = Optional.empty();
		try(Session session = SessionProvider.getSession()) {
			auction = Optional.of(session.load(Auction.class, id));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return auction;
	}

	public long addAuction(String title, String description, long sellerUserId) {
		long id = 0;
		try(Session session = SessionProvider.getSession()) {
			Auction auction = new Auction(title, description, sellerUserId);
			session.beginTransaction();
			session.save(auction);
			session.getTransaction().commit();
			id = auction.getId();
			logger.info("Book " + auction.getTitle() + " is successfully added to the auction.");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return id;
	}
	
	private List<Auction> getAuctions(boolean status) {
		List<Auction> auctions = new ArrayList<>();
		try(Session session = SessionProvider.getSession()) {
			Query query = session.createQuery("FROM Auction A WHERE A.IS_ACTIVE = :status");
			query.setParameter("status", status);
			auctions = query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return auctions;
	}
}
