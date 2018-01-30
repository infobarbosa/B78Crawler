package com.infobarbosa.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MockPageMessageBus implements PageMessageBus {
    private static final Logger logger = LoggerFactory.getLogger(MockPageMessageBus.class);

    @Override
    public void enqueuePageUrl(String pageUrl) {
        logger.debug("enqueuePageUrl was called");
    }

    @Override
    public List<String> dequeueNextPageUrl() {
        logger.debug("dequeueNextPageUrl was called");
        return null;
    }

    @Override
    public void destroy() {
        logger.debug("destroy was called");
    }
}
