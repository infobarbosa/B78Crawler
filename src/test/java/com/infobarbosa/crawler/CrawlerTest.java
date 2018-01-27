package com.infobarbosa.crawler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
@RunWith(MockitoJUnitRunner.class)
public class CrawlerTest{
    private static Logger logger = LoggerFactory.getLogger(CrawlerTest.class);


    @Mock
    private PageRepository pageRepo;

    @InjectMocks
    private Crawler crawler;
    
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
