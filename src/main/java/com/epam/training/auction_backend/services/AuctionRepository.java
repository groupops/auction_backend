package com.epam.training.auction_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction_backend.entity.Auction;

@Transactional
public interface AuctionRepository extends CrudRepository<Auction, Long> {
	public Optional<AuctionTransferObject> findOne(long id);
	public Auction save(Auction auction);
	public List<AuctionTransferObject> findByIsActive(boolean isActive);
}
