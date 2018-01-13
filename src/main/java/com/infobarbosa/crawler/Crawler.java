package com.infobarbosa.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler {
	
	private static Logger logger = LoggerFactory.getLogger( Crawler.class );


	public static void main(String args[]){
  		String[] urls = {
  				"https://www.americanas.com.br/produto/132429722"
				,"https://www.americanas.com.br/produto/132127601"
  		};

		for(String url: urls)
			craw(url);
	}

	private static void craw(String url){
		try
		{
			Document doc = Jsoup.connect( url ).get();

			//printLink(doc);
			printProduct(doc);
			printPrice(doc);
			listLinks(url);
		}catch(IOException e){
			logger.error( e.toString() );
		}catch(Exception e){
			logger.error( e.toString() );
			
		}
	}

	private static void printProduct(Document doc){
		Element content = doc.getElementById("content");
		Elements elements = content.getElementsByTag("h1");
		for (Element e : elements) {
			String key = e.attr("class");
			if(key.equals("product-name")){
				String value = e.text();
				System.out.println( key + " ------> "  + value);
			}
		}

	}
	private static void printLink(Document doc){
		System.out.println("entrou");
		Element content = doc.getElementById("head");
		System.out.println(content);
		Elements links = content.getElementsByTag("link");
		for (Element link : links) {
			System.out.println(link);
			String linkHref = link.attr("href");
			String linkText = link.text();
			System.out.println( linkHref + " : "  + linkText);
		}

	}

	private static void printPrice(Document doc){

		Element content = doc.getElementById("content");
		Elements elements = content.getElementsByTag("p");
		for (Element e : elements) {
			String key = e.attr("class");
			if(key.equals("sales-price")){
				String value = e.text();
				System.out.println( key + " ------> "  + value);
			}
			//persistOnKafka(linkHref);
		}

	}

	private static void listLinks(String url){
		try{
			ResourceLinks.list(url);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

//	private void persistOnKafka(String url){
//		Properties props = new Properties();
//		 props.put("bootstrap.servers", "172.18.0.21:9092");
//		 props.put("acks", "all");
//		 props.put("retries", 0);
//		 props.put("batch.size", 16384);
//		 props.put("linger.ms", 1);
//		 props.put("buffer.memory", 33554432);
//		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//		 Producer<String, String> producer = new KafkaProducer<>(props);
//
//		 producer.send(new ProducerRecord<String, String>("teste2", this.url, url));
//
//		 producer.close();
//
//	}
}
