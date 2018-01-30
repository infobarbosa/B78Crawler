package com.infobarbosa.crawler;

import java.util.List;

public interface PageMessageBus {

    /**
     * enqueue a page url
     * */
    public void enqueuePageUrl(String pageUrl);

    /**
     * dequeue the next page url
     * */
    public List<String> dequeueNextPageUrl();

    /**
     * libera os recursos utilizados
     * */
    public void destroy();
}
