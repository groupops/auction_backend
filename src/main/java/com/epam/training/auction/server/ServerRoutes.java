package com.epam.training.auction.server;

import com.epam.training.auction.common.AuctionsService;
import com.epam.training.auction.common.BiddingService;
import com.epam.training.auction.common.UsersService;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServerRoutes {
    @Autowired
    private UsersService usersService;
    @Autowired
    private BiddingService biddingService;
    @Autowired
    private AuctionsService auctionsService;

    @Bean
    RoutesBuilder myRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:users").bean(usersService, "addUser");
                from("activemq:queue:auctions").bean(auctionsService);
                from("activemq:queue:bids").bean(biddingService);
            }
        };
    }
}
