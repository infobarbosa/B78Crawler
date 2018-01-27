package com.infobarbosa.crawler;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class PageRepositoryCassandra implements PageRepository {

    private static Logger logger = LoggerFactory.getLogger(PageRepositoryCassandra.class);
    private static Session session = null;

    /**
     * constructor
     * */
    public PageRepositoryCassandra(){
        Cluster cluster = Cluster.builder()
                .addContactPoint("172.17.0.2")
                .build();
        session = cluster.connect();
        logger.info("sessao cassandra inicializada");
    }

    /**
     * Add a page to a repository
     * */
    @Override
    public void addPageUrl(String pageUrl){
        logger.debug("inserindo url: " + pageUrl);
        PreparedStatement prepared = session.prepare(
                "insert into crawler.pages (url) values (?)");

        session.execute(prepared.bind(pageUrl));
    }
}
