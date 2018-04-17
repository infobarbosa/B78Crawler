package com.infobarbosa.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class CrawlerConfig {
    private static final Logger logger = LoggerFactory.getLogger( CrawlerConfig.class );

    private static Properties props = new Properties();

    public static final String CASSANDRA_CONTACT_POINT = System.getenv("B78CRAWLER_CASSANDRA_CONTACT_POINT");

    public static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG = System.getenv( "B78CRAWLER_KAFKA_BOOTSTRAP_SERVERS_CONFIG" );

    public static final String KAFKA_PENDING_PAGES_TOPIC = "pending_pages";

}
