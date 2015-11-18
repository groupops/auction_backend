package com.epam.training.auction.services;

import com.epam.training.auction.common.AuctionsService;
import com.epam.training.auction.exception.ItemNotFoundException;
import com.epam.training.auction.repository.AuctionRepository;
import com.epam.training.auction.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.anyLong;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;

/**
 * Created by Yaroslav_Kynyk on 11/2/2015.
 */
public class AuctionsServiceImplTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private UserRepository userRepository;

    private AuctionsService auctionsService;

    @Before
    public void setUp() {
        initMocks(this);
        auctionsService = new AuctionsServiceImpl(userRepository, auctionRepository);
    }

    @Test(expected = ItemNotFoundException.class)
    public void getAuctionIsNull() {
        when(auctionRepository.findOne(anyLong())).thenReturn(null);
        auctionsService.getAuctionById(5L);
    }
}