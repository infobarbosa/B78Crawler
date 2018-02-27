package com.infobarbosa.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class CrawlerConfig {
    private static final Logger logger = LoggerFactory.getLogger( CrawlerConfig.class );

    private static Properties props = new Properties();

    public static final String CASSANDRA_CONTACT_POINT = System.getenv("CASSANDRA_CONTACT_POINT");

    public static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG = System.getenv( "KAFKA_BOOTSTRAP_SERVERS_CONFIG" );

}
