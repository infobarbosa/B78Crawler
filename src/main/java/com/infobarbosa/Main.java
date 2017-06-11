package com.infobarbosa;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main{
	
	private static Logger logger = LoggerFactory.getLogger( Main.class );

	String url;
	
  	public Main(String url){
	    this.url = url;
  	}
	
	public static void main(String args[]){
		Main m = new Main("https://en.wikipedia.org/wiki/Main_Page");
		
		m.craw();
	}

	public void craw(){
		try
		{
			Document doc = Jsoup.connect( this.url ).get();
			
			Element content = doc.getElementById("content");
			Elements links = content.getElementsByTag("a");
			for (Element link : links) {
			  String linkHref = link.attr("href");
			  String linkText = link.text();
			  System.out.println( linkHref + " ------> "  + linkText);
			  persistOnKafka(linkHref);
			}
		}catch(IOException e){
			logger.error( e.toString() );
		}catch(Exception e){
			logger.error( e.toString() );
			
		}
	}
	
	private void persistOnKafka(String url){
		Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 Producer<String, String> producer = new KafkaProducer<>(props);
		 
		 producer.send(new ProducerRecord<String, String>("teste1", this.url, url));

		 producer.close();

	}
}
