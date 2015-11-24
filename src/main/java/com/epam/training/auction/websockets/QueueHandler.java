package com.epam.training.auction.websockets;

import com.epam.training.auction.common.UserBidTransferObject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Receives messages from ActiveMQ and relays them to appropriate users.
 */
@Component(value = "queueHandler")
public class QueueHandler {

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
        // send the message specifically to the destination user by using STOMP's user-directed messaging
        messageTemplate.convertAndSendToUser(bid.getUser().getUsername(), "/topic/messages", bid, defaultHeaders);
    }
}
