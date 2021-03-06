package com.infobarbosa.crawler;


import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Singleton;

@Singleton
public class PageRepositoryCassandra implements PageRepository {

    private static Logger logger = LoggerFactory.getLogger(PageRepositoryCassandra.class);
    private static Session session = null;
    private PreparedStatement preparedCheck = null;
    private PreparedStatement preparedPageDetail = null;
    PreparedStatement preparedPages = null;

    /**
     * constructor
     * */
    public PageRepositoryCassandra(){
        Cluster cluster = Cluster.builder()
                .addContactPoint( CrawlerConfig.CASSANDRA_CONTACT_POINT )
                .build();
        session = cluster.connect();
        logger.info("sessao cassandra inicializada");

        preparedCheck = session.prepare(
                "select count(1) as qtt from crawler.page_detail where page = ?");

        preparedPageDetail = session.prepare(
                "insert into crawler.page_detail (page, page_detail) values (?, ?)");

        preparedPages = session.prepare(
                "insert into crawler.pages (parent_page, child_page) values (?, ?)");
    }

    /**
     * Add a page to a repository
     * */
    @Override
    public void addPage(Page page){
        logger.debug("inserindo url: " + page.getUrl());

        //insert CRAWLER.PAGE_DETAIL
        session.execute( preparedPageDetail.bind(page.getUrl(), page.getPageDetail()) );

        //insert CRAWLER.PAGES
        page.getLinks().forEach((url)->{
            session.execute( preparedPages.bind(page.getUrl(), url) );
        });

        logger.debug("pagina inserida");
    }

    /**
     * check the page was already crawled
     * */
    @Override
    public boolean checkThePageWasCrawledAlready(String url){

        logger.debug("check if url war crawled alread: " + url);
        ResultSet r1 = session.execute(preparedCheck.bind(url));

        Row row = r1.one();

        long result = row.getLong("qtt");

        logger.debug("checkThePageWasCrawledAlready - result: " + result);

        if( result  > 0 ) {
            logger.info("URL ja foi visitada");
            return true;
        }

        return false;
    }

    /**
     * destroy method
     * */
    public void destroy(){
        logger.debug("destroy was called");
        
        try{
            session.getCluster().close();
            session.close();
        }catch(Exception e){
            logger.error("problemas liberando session.", e);
        }finally{
            session = null;
        }
    }
}
