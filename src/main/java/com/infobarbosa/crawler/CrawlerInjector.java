package com.infobarbosa.crawler;

import com.google.inject.AbstractModule;

public class CrawlerInjector extends AbstractModule {

    @Override
    protected void configure() {
        //bind the service to implementation class
        //bind(MessageService.class).to(EmailService.class);

        //bind MessageService to Facebook Message implementation
        bind(MessageService.class).to(FacebookService.class);

    }

}