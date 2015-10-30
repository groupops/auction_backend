package com.epam.training.auction;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRouterConfiguration {
 
  @Bean
  RoutesBuilder myRouter() {
    return new RouteBuilder() {
 
      @Override
      public void configure() throws Exception {
          System.out.println("INIT");
          from("jms:queue:auctions").to("auctionsServiceImpl");
          from("jms:queue:users").to("usersServiceImpl");
          from("jms:queue:bidding").to("biddingServiceImpl");
      }
 
    };
  }
 
}
