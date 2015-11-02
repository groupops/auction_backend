package com.epam.training.auction_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction_backend.entity.Auction;

@Transactional
@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {
    Auction findOne(Long id);

    Auction save(Auction auction);

    List<Auction> findByActive(boolean active);
}
