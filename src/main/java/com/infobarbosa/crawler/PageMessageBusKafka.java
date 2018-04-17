package com.infobarbosa.crawler;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Singleton
public class PageMessageBusKafka implements PageMessageBus {
    private static Logger logger = LoggerFactory.getLogger( PageMessageBusKafka.class );

    private static Producer<String, String> producer = null;
    private static Consumer<String, String> consumer = null;

    /**
     * Construtor
     * */
    public PageMessageBusKafka(){
        initProducer();
        initConsumer();
    }

    /**
     * newProducer inicializa um produtor para o kafka
     * TODO externalizar configuracoes de endpoints
     * */
    private void initProducer(){
        Properties config = new Properties();
        try {
            config.put("client.id", InetAddress.getLocalHost().getHostName());
        }catch(UnknownHostException he){
            logger.error(he.getMessage());
        }

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, CrawlerConfig.KAFKA_BOOTSTRAP_SERVERS_CONFIG);
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<String, String>(config);
    }

    /**
     * initConsumer inicializa um consumidor para o kafka
     * TODO externalizar configuracoes de endpoints
     * */
    private void initConsumer(){
        Properties config = new Properties();
        try {
            config.put("client.id", InetAddress.getLocalHost().getHostName());
        }catch(UnknownHostException he){
            logger.error(he.getMessage());
        }

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, CrawlerConfig.KAFKA_BOOTSTRAP_SERVERS_CONFIG);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,  "1000");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "crawler");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<String, String>(config);
        consumer.subscribe(Collections.singletonList(CrawlerConfig.KAFKA_PENDING_PAGES_TOPIC));
    }

    /**
     * put a page url into the queue
    */
    @Override
    public void enqueuePageUrl(String url){
        producer.send(new ProducerRecord<>(CrawlerConfig.KAFKA_PENDING_PAGES_TOPIC, url, url));
        logger.info("URL enfileirada: " + url);
    }

    /**
     *  get the next page url from the queue
     * */
    @Override
    public List<String> dequeueNextPageUrl() {
        List<String> urls = new ArrayList<>();
        String url = null;
        ConsumerRecords<String, String> records = consumer.poll(0);
        logger.debug("after poll");
        for (ConsumerRecord<String, String> record : records) {
            logger.debug("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            urls.add( record.value() );
        }

        return urls;
    }

    @Override
    public void destroy() {
        try{
            producer.close();
            consumer.close();
        } catch(Exception e){
            logger.error("Problemas liberando recursos.");
        } finally{
            producer = null;
            consumer = null;
        }
    }
}
