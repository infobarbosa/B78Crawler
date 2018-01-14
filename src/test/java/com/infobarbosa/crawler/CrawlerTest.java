package com.infobarbosa.crawler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CrawlerTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CrawlerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CrawlerTest.class );
    }

    /**
     * Teste da classe principal
     */
    public void testApp()
    {
        Crawler m = new Crawler();
        m.crawl("https://www.americanas.com.br/produto/132429722");

        assertTrue( true );
    }
}
