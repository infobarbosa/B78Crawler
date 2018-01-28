package com.infobarbosa.crawler;

public interface PageRepository {

    /**
     * Add a page to a repository
     * */
    public void addPage(Page page);

    /**
     * check if the page was already crawled
     * */
    boolean checkThePageWasCrawledAlready(String url);

    /**
     * destroy method
     * */
    public void destroy();
}
