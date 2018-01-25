package com.infobarbosa.crawler;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageRepositoryCassandra implements PageRepository {

    private static Logger logger = LoggerFactory.getLogger(PageRepositoryCassandra.class);
    private static PageRepository instance = null;
    private static Cluster cluster = null;
    private static Session session = null;

    /**
     * constructor
     * */
    private PageRepositoryCassandra(){
        cluster = Cluster.builder()
                .addContactPoint("172.17.0.2")
                .build();
        session = cluster.connect();
        logger.info("sessao cassandra inicializada");
    }

    /**
     * return a singleton instance
     * */
    public static PageRepository getInstance(){
        if(instance == null || session == null)
            instance = new PageRepositoryCassandra();

        return instance;
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
    };
}
