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
     * TODO externalizar a configuracao de endpoints
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
    public void addPage(Page page){
        logger.debug("inserindo url: " + page.getUrl());

        //insert CRAWLER.PAGE_DETAIL
        PreparedStatement pDetail = session.prepare(
                "insert into crawler.page_detail (page, detail) values (?, ?)");

        session.execute( pDetail.bind(page.getUrl(), page.getPageDetail()) );


        //insert CRAWLER.PAGES
        PreparedStatement prepared = session.prepare(
                "insert into crawler.pages (parent_page, child_page) values (?, ?)");

        page.getLinks().forEach((url)->{
            session.execute( prepared.bind(page.getUrl(), url) );
        });
    }
}
