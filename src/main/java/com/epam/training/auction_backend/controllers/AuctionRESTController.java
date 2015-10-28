package com.epam.training.auction_backend.controllers;

import com.epam.training.auction_backend.model.Message;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AuctionRESTController {

  @Autowired
  private CamelContext camelContext;

  @Autowired
  @Qualifier("sessionRegistry")
  private SessionRegistry sessionRegistry;

  @RequestMapping(method = RequestMethod.GET, value = "/auctions")
  public List<String> getAuctions() {
    return Arrays.asList("Auction A", "Auction B", "Auction C");
  }

  @RequestMapping(method = RequestMethod.GET, value = "/auction/{auctionId}")
  public String getAuction(@PathVariable Integer auctionId) {
    return "Auction " + auctionId;
  }

  @RequestMapping(method = RequestMethod.POST,
      value = "/auction/{auctionId}/bid/{amount}",
      consumes = "application/json")
  public void doBid(@PathVariable Long auctionId,
                      @PathVariable Integer amount,
                      Principal currentUser) {
    Message message = new Message();
    String sender = currentUser.getName();
    message.setAuctionId(auctionId);
    message.setAmount(amount);
    message.setSender(sender);
    camelContext.createProducerTemplate()
        .sendBody("activemq:bid_messages", message);
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET,
      produces = "application/json")
  public Set<String> getLoggedUsers() {
    return sessionRegistry.getAllPrincipals().stream()
        .map(u -> ((User) u).getUsername()).collect(Collectors.toSet());
  }
}
