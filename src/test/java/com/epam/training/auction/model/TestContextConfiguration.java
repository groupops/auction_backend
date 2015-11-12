package com.epam.training.auction.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Dmytro_Ulanovych on 11/2/2015.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.epam.training.auction"})
@Configuration
@PropertySource("classpath:application.properties")
public class TestContextConfiguration {
}
