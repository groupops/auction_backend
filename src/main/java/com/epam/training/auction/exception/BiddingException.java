package com.epam.training.auction.exception;

/**
 * Created by Dmytro_Ulanovych on 11/2/2015.
 */
public class BiddingException extends RuntimeException {

    public BiddingException(String message) {
        super(message);
    }

    public BiddingException(Throwable cause) {
        super(cause);
    }
}
