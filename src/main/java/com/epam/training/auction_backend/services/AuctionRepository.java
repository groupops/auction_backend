package com.epam.training.auction_backend.services;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.epam.training.auction_backend.entity.Auction;

@Transactional
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByActiveTrue();
    List<Auction> findByActiveFalse();
}
