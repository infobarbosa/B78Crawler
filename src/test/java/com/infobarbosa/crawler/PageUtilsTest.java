package com.infobarbosa.crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PageUtilsTest{
    private static Logger logger = LoggerFactory.getLogger(PageUtilsTest.class);

    private Document doc = null;
    private PageUtils pageUtils = null;

    /**
     * inicializa o teste
     * */
    @Before
    public void init(){
        logger.info("inicializando o teste");
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p>" +
                "<p><a href=\"http://test.link\">Simple link</a></p>" +
                "</body></html>";
        doc = Jsoup.parse(html);
        logger.debug(doc.toString());

        pageUtils = PageUtils.getInstance();
    }

    /**
     * Teste da classe principal
     */
    @Test
    public void testObtemLinksDeUmDocumento(){
        ArrayList<String> links = pageUtils.list(doc);
        if(links.size() == 0)
            fail("nenhum link encontrado");
        else
            assertTrue(links.get(0).equals("http://test.link"));
    }
}
