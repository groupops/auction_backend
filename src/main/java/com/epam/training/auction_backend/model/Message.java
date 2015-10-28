package com.epam.training.auction_backend.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Message implements Serializable {

  private static final long serialVersionUID = 1L;

  public Date date;
  public String content;
  public String to;
  public String from;

  public Message() {
    this.date = Calendar.getInstance().getTime();
  }
}