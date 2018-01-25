package com.infobarbosa.crawler;

public interface PageBroker {

    /**
     * enqueue a page url
     * */
    public void enqueuePageUrl(String pageUrl);

    /**
     * dequeue the next page url
     * */
    public String dequeueNextPageUrl();
}
