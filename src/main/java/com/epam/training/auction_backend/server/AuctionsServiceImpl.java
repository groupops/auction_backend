package com.epam.training.auction_backend.server;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction.common.AuctionsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "auctionsServiceImpl")
public class AuctionsServiceImpl implements AuctionsService {
  @Override
  public List<AuctionTransferObject> getActiveAuctions() {
    return null;
  }

  @Override
  public List<AuctionTransferObject> getArchivedAuctions() {
    return null;
  }

  @Override
  public void addAuction(AuctionTransferObject auctionTransferObject) {

  }

  @Override
  public Optional<AuctionTransferObject> getAuctionById(long l) {
    return null;
  }
}
