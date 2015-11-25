package com.epam.training.auction.websockets;

import com.epam.training.auction.common.UserBidTransferObject;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class APIController {

    @Autowired
    private CamelContext camelContext;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    /**
     * Receives the messages from clients and sends them to ActiveMQ.
     *
     * @param message the message to send, encapsulated in a wrapper
     */
    @RequestMapping(value = "/auctions/bid", method = RequestMethod.POST, consumes = "application/json")
    public void sendMessage(@RequestBody UserBidTransferObject message, Principal currentUser) {
        // send any message sent by clients to a queue called queue:bids
        camelContext.createProducerTemplate().sendBody("activemq:queue:bids", message);
    }

    /**
     * Returns the names of the currently logged-in users.
     * 
     * @return set of user names
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public Set<String> getUsers() {
        return sessionRegistry.getAllPrincipals().stream().map(u -> ((User) u).getUsername()).collect(Collectors.toSet());
    }
}
