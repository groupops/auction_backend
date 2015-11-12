package com.epam.training.auction.repository;

import com.epam.training.auction.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByActiveTrue();

    List<Auction> findByActiveFalse();
}
