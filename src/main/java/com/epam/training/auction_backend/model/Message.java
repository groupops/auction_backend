package com.epam.training.auction_backend.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class Message implements Serializable {

  private static final long serialVersionUID = 1L;

  private Date date;
  private Long auctionId;
  private Integer amount;
  private String sender;
  private String receiver;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Long getAuctionId() {
    return auctionId;
  }

  public void setAuctionId(Long auctionId) {
    this.auctionId = auctionId;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public Message() {
    LocalDateTime localDateTime = LocalDateTime.now();
    this.date = Timestamp.valueOf(localDateTime);
  }
}