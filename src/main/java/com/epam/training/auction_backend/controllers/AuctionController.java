package com.epam.training.auction_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;

import com.epam.training.auction_backend.entity.Auction;

public class AuctionController {

	public List<Auction> getActiveAuctions() {
		return null;
		
	}
	
	public List<Auction> getArchivedAuctions() {
		return null;
		
	}
	
	public Optional<Auction> getAuctionsAuctionById(long id) {
		return null;
		
	}
	
	public long addAuction(String title, String description, long sellerUserId) {
		Auction auction = new Auction(title, description, sellerUserId);
		long auctionId = create(auction);
		return auctionId;
	}
	
	public static long create(Auction auction) {
		Session session = SessionProvider.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(auction);
		session.getTransaction().commit();
		session.close();
		System.out.println("Auction " + auction.toString() + " is successfully created.");
		return auction.getId();
	}
}
