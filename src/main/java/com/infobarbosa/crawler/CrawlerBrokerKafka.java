package com.infobarbosa.crawler;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;

public class Kafka {
    private static Logger logger = LoggerFactory.getLogger( Kafka.class );

    private static Kafka kafka = null;
    private static Producer<String, String> producer = null;
    private static Consumer<String, String> consumer = null;

    /**
     * Construtor
     * */
    private Kafka(){
        initProducer();
        initConsumer();
    }

    /**
     * getInstance retorna um singleton
     * */
    public static Kafka getInstance(){
        if(kafka == null){
            kafka = new Kafka();
        }

        return kafka;
    }

    /**
     * newProducer inicializa um produtor para o kafka
     * */
    private void initProducer(){
        Properties config = new Properties();
        try {
            config.put("client.id", InetAddress.getLocalHost().getHostName());
        }catch(UnknownHostException he){
            logger.error(he.getMessage());
        }

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:29092");
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(config);
    }

    /**
     * initConsumer inicializa um consumidor para o kafka
     * */
    private void initConsumer(){
        Properties config = new Properties();
        try {
            config.put("client.id", InetAddress.getLocalHost().getHostName());
        }catch(UnknownHostException he){
            logger.error(he.getMessage());
        }

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:29092");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,  "1000");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "crawler");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<String, String>(config);
        consumer.subscribe(Arrays.asList("webpages"));
    }

    /**
     * enqueue method
    */
    public void enqueue(String url){
        producer.send(new ProducerRecord<>("webpages", url, url));
        logger.info("URL enfileirada: " + url);
    }

    /**
     * getNextUrl obtem o proximo url da fila
     * */
    public String getNextUrl() {
        String url = null;
        ConsumerRecords<String, String> records = consumer.poll(1);
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            url = record.value();
        }

        return url;
    }
}
