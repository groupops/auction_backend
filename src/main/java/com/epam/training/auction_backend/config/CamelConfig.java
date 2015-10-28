package com.epam.training.auction_backend.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class CamelConfig {

  @Bean
  ConnectionFactory jmsConnectionFactory() {
    PooledConnectionFactory pool = new PooledConnectionFactory();
    pool.setConnectionFactory(
        new ActiveMQConnectionFactory("tcp://localhost:61616"));
    return pool;
  }

  @Bean
  RouteBuilder myRouter() {
    return new RouteBuilder() {

      @Override
      public void configure() throws Exception {
        from("activemq:bid_messages").to("bean:queueHandler");
      }
    };
  }
}
