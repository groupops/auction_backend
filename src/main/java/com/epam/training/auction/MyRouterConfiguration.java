package com.epam.training.auction;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.auction_backend.services.UsersServiceImpl;

@Configuration
public class MyRouterConfiguration {

    @Bean
    public RoutesBuilder myRouter() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("jms:queue:auctions").to("bean:auctionsServiceImpl");
                from("jms:queue:users").to("bean:usersServiceImpl");
                from("jms:queue:bidding").to("bean:biddingServiceImpl");
            }
        };
    }

}
