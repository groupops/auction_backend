package com.epam.training.auction.util;

import com.epam.training.auction.common.AuctionTransferObject;
import com.epam.training.auction.entity.Auction;
import com.epam.training.auction.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionMapper {

    public static AuctionTransferObject map(Auction auction) {
        User user = auction.getSellerUser();
        return AuctionTransferObject.getBuilder(auction.getTitle(), UserMapper.map(user))
                .setId(auction.getId())
                .setDescription(auction.getDescription())
                .setIsActive(auction.getActive())
                .setCreatedAt(LocalDateTime.ofInstant(auction.getCreatedAt().toInstant(), ZoneId.systemDefault()))
                .build();
    }

    public static List<AuctionTransferObject> map(List<Auction> auctions) {
        return auctions.stream().map(AuctionMapper::map).collect(Collectors.toList());
    }
}
