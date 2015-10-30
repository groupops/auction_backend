package com.epam.training.auction;

import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.camel.spring.boot.FatWarInitializer;

import com.epam.training.auction_backend.server.ServerRoutes;

public final class ServletInitializer extends FatWarInitializer {
    
    @Override
    protected Class<? extends FatJarRouter> routerClass() {
        return ServerRoutes.class;
    }

}
