package com.epam.training.auction_backend.controllers;


import com.epam.training.auction_backend.model.Message;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class APIController {

  @Autowired
  private CamelContext camelContext;

  @Autowired
  @Qualifier("sessionRegistry")
  private SessionRegistry sessionRegistry;

  @RequestMapping(value = "/send", method = RequestMethod.POST,
      consumes = "application/json")
  public void sendMessage(@RequestBody Message message, Principal currentUser) {
    message.from = currentUser.getName();
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
