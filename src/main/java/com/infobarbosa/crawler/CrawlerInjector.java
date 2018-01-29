package com.infobarbosa.crawler;

import com.google.inject.AbstractModule;

public class CrawlerInjector extends AbstractModule {

    @Override
    protected void configure() {

        bind(PageRepository.class).to(PageRepositoryCassandra.class);
        bind(PageMessageBus.class).to(PageMessageBusKafka.class);
    }

}