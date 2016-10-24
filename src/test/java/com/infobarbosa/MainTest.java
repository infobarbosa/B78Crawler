package com.infobarbosa;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.infobarbosa.Main;

/**
 * Unit test for simple App.
 */
public class MainTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MainTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MainTest.class );
    }

    /**
     * Teste da classe principal
     */
    public void testApp()
    {
        Main m = new Main("https://pt.wikipedia.org/wiki/Louis_Armstrong");
        m.craw();

        assertTrue( true );
    }
}
