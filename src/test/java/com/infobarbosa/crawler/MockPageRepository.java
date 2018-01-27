package com.infobarbosa.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockPageRepository implements PageRepository {
    private static Logger logger = LoggerFactory.getLogger(MockPageRepository.class);

    @Override
    public void addPageUrl(String pageUrl) {
        logger.debug("mock class was called");
    }
}
