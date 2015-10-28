package com.epam.training.auction_backend.handlers;

import com.epam.training.auction_backend.model.Message;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.util.HashMap;
import java.util.Map;

@Component(value = "queueHandler")
public class QueueHandler {

  @Autowired
  private SimpMessageSendingOperations msgTemplate;

  private static Map<String, Object> defaultHeaders;

  static {
    defaultHeaders = new HashMap<String, Object>();
    defaultHeaders
        .put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
  }

  public void handle(Exchange exchange) throws InterruptedException {
    org.apache.camel.Message camelMessage = exchange.getIn();
    Message message = camelMessage.getBody(Message.class);
    // send the message specifically to the destination user by using STOMP's user-directed messaging
    msgTemplate.convertAndSendToUser(message.to, "/topic/messages", message,
        defaultHeaders);
  }
}
