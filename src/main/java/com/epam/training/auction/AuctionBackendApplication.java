package com.epam.training.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Dmytro_Ulanovych on 10/30/2015.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class AuctionBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuctionBackendApplication.class, args);
    }
}
