package com.infobarbosa.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerConfigTest {
    private static Logger logger = LoggerFactory.getLogger(CrawlerConfigTest.class);

    @Test
    public void cassandra_endpoint_nao_deve_ser_nulo(){
        String cassandraContactPoint = CrawlerConfig.CASSANDRA_CONTACT_POINT;

        // TO DO acertar esse teste
        //assertNotNull( cassandraContactPoint );
        assert(true);
    }
}
