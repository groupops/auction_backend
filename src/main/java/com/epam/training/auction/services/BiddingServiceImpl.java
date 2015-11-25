package com.epam.training.auction.services;

import com.epam.training.auction.common.BiddingService;
import com.epam.training.auction.common.UserBidTransferObject;
import com.epam.training.auction.model.BiddingStore;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.HashMap;
import java.util.Map;

@Service(value = "biddingServiceImpl")
public class BiddingServiceImpl implements BiddingService {
    private final BiddingStore biddingStore;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    public BiddingServiceImpl(BiddingStore biddingStore) {
        this.biddingStore = biddingStore;
    }

    @Override
    public UserBidTransferObject getMaxBid(long auctionId) {
        return biddingStore.getMaxBid(auctionId);
    }

    @Override
    public boolean bid(UserBidTransferObject bid) {
        camelContext.createProducerTemplate().sendBody("activemq:queue:bids", bid);
        return biddingStore.makeBid(bid.getAuctionId(), bid.getBid(), bid.getUser().getId());
    }

    /**
     * Receives messages from ActiveMQ and relays them to appropriate users.
     */
    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    private static Map<String, Object> defaultHeaders;

    static {
        defaultHeaders = new HashMap<>();
        defaultHeaders.put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
    }

    public void handle(Exchange exchange) {
        Message camelMessage = exchange.getIn();
        UserBidTransferObject bid = camelMessage.getBody(UserBidTransferObject.class);
        messageTemplate.convertAndSendToUser(bid.getUser().getUsername(), "/topic/messages", bid, defaultHeaders);
    }

}
