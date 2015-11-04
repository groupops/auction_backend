package com.epam.training.auction_backend.util;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction_backend.entity.Auction;
import com.epam.training.auction_backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("auctionMapper")
public class AuctionMapper {

    public static AuctionTransferObject map(Auction auction) {
        User user = auction.getSellerUser();
        return AuctionTransferObject.getBuilder(auction.getTitle(), UserMapper.map(user))
                .setId(auction.getId())
                .build();
    }

    public static List<AuctionTransferObject> map(List<Auction> auctions) {
        return auctions.stream().map(AuctionMapper::map).collect(Collectors.toList());
    }
}
