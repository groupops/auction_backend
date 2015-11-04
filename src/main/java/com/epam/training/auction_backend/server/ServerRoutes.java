package com.epam.training.auction_backend.server;

import org.apache.camel.builder.RouteBuilder;


public class ServerRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jms:queue:auctions").to("auctionsServiceImpl");
        from("jms:queue:users").to("usersServiceImpl");
        from("jms:queue:bidding").to("biddingServiceImpl");
    }

}
