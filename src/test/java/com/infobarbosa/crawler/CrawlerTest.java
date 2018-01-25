package com.infobarbosa.crawler;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class CrawlerTest{
    private static Logger logger = LoggerFactory.getLogger(CrawlerTest.class);

    private Crawler crawler = null;

    /**
     * inicializa a classe de teste
     * */
    @Before
    public void init(){
        crawler = Crawler.getInstance();
    }

    /**
     * Teste o crawling de uma pagina de e-commerce que possui dados de produto
     */
    @Test
    public void testCrawlingUsandoPaginaComProduto() {
        logger.debug("pagina com produto");
        crawler.crawl("https://www.americanas.com.br/produto/132429722");

        assertTrue( true );
    }

    /**
     * Teste o crawling de uma pagina de e-commerce que nao possui dados de produto
     */
    @Test
    public void testCrawlingUsandoPaginaSemProduto() {
        logger.debug("pagina sem produto");
        crawler.crawl("https://www.americanas.com.br/categoria/tv-e-home-theater");

        assertTrue( true );
    }

}
