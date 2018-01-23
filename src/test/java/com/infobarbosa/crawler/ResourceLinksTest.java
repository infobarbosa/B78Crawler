package com.infobarbosa.crawler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class ResourceLinksTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ResourceLinksTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ResourceLinksTest.class );
    }

    /**
     * Teste da classe principal
     */
//    public void testObtemLinksDeUmDocumento()
//    {
//        String url = "https://www.americanas.com.br/produto/132429722";
//        try {
//            Document doc = Jsoup.connect(url).get();
//            ResourceLinks resourceLinks = new ResourceLinks();
//            ArrayList<String> links = resourceLinks.list(doc);
//
//            if(links.size() > 0){
//                assertTrue(true);
//            }else{
//                fail("nenhum link encontrado.");
//            }
//        }catch(IOException ioe){
//            fail("falhou. ".concat(ioe.getMessage()));
//        }
//    }
}
