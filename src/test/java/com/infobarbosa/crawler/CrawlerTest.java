package com.infobarbosa.crawler;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.*;
import org.junit.runner.RunWith;
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

    private Crawler crawler;

    /**
     * inicializa a classe de teste
     * */
    @Before
    public void init(){
        Injector injector = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(PageRepository.class).to(MockPageRepository.class);
                        bind(PageMessageBus.class).to(MockPageMessageBus.class);
                    }
                }
        );

        crawler = injector.getInstance(Crawler.class);
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

    @Test
    public void testCrawlingAImageUrl(){
        logger.debug("crawling de url que liga a uma imagem");
        crawler.crawl("https://images-americanas.b2w.io/produtos/01/00/item/132429/7/132429722_4SZ.jpg");

        assertTrue(true);
    }

}
