package com.epam.training.auction_backend.exception;

import com.epam.training.auction_backend.entity.Auction;

/**
 * Created by Yaroslav_Kynyk on 11/2/2015.
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
