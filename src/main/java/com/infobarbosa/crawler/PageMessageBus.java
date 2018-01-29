package com.infobarbosa.crawler;

public interface PageMessageBus {

    /**
     * enqueue a page url
     * */
    public void enqueuePageUrl(String pageUrl);

    /**
     * dequeue the next page url
     * */
    public String dequeueNextPageUrl();

    /**
     * libera os recursos utilizados
     * */
    public void destroy();
}
