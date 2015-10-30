package com.epam.training.auction_backend.server;

import com.epam.training.auction.common.BiddingService;
import com.epam.training.auction.common.UserBid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "biddingServiceImpl")
public class BiddingServiceImpl implements BiddingService {
  @Override
  public Optional<UserBid> getMaxBid(long l) {
    return null;
  }

  @Override
  public void bid(long l, double v, long l1) {

  }
}
