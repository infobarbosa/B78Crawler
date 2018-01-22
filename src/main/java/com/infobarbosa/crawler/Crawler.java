package com.infobarbosa.crawler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.infobarbosa.crawler.kafka.Kafka;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler {
	
	private static Logger logger = LoggerFactory.getLogger( Crawler.class );

	Kafka kafka = null;

	/**
	 * Construtor
	 * */
	public Crawler(){
		kafka = Kafka.getInstance();
	}

	/**
	 * main method
	 * */
	public static void main(String args[]){

  		Crawler crawler = new Crawler();
  		crawler.crawl();
	}

	/**
	 * crawl sem argumentos (obtem o proximo url de uma fila)
	 * */
	public void crawl(){
		String url = null;
		while(true) {
			url = kafka.getNextUrl();
			crawl(url);
		}
	}

	/**
	 * obtem os dados a partir do URL fornecido
	 * */
	public void crawl(String url){
		try
		{
			Document doc = Jsoup.connect( url ).get();

			WebPage webPage = new WebPage();
			webPage.setUrl(url);
			getLinks(doc, webPage);
			getProduct(doc, webPage);

			System.out.println(webPage);
		} catch(Exception e){
			logger.error( e.toString() );
		}
	}

	/**
	 * Obtem a lista de links do documento e adiciona aa lista da web page.
	 * */
	private void getLinks(Document doc, WebPage webPage){

		try {
			URI uri = new URI(doc.baseUri());
			System.out.println("URI(root): " + uri.getHost());

			ResourceLinks resourceLinks = new ResourceLinks();
			ArrayList<String> links = resourceLinks.list(doc);

			for(String link: links) {
				webPage.addLink(link);
				kafka.enqueue(link);
			}

		}catch(IOException ioe){
			logger.error("Falha obtendo lista de links. ".concat(ioe.getMessage()));
		}catch(URISyntaxException se){
			se.printStackTrace();
		}
	}

	/**
	 * Obtem os dados do produto a partir do documento e atualiza a variavel webPage
	 * */
	private void getProduct(Document doc, WebPage webPage){

		Product product = new Product();
		product.setUrl(doc.location());

		getProductPrice(doc, product);
		getProductName(doc, product);
		getProductID(doc, product);

		System.out.println(product);
		webPage.setProduct(product);
	}


	private void getProductID(Document doc, Product product){
		Element content = doc.getElementById("content");
		Elements metas = content.getElementsByTag("meta");
		for(Element e: metas){

			String key = e.attr("itemProp");
			if(key.equals("productID")){
				String value = e.attr("content");
				product.setId(value);
			}
		}
	}

	/**
	 * Obtem a descricao/nome do produto
	 * */
	private void getProductName(Document doc, Product product){
		Element content = doc.getElementById("content");
		Elements elements = content.getElementsByTag("h1");
		//Elements elements = content.getElementsByAttribute("product-name");

		for (Element e : elements) {
			String key = e.attr("class");
			if(key.equals("product-name")){
				String value = e.text();
				product.setDescription(value);
			}
		}
	}

	/**
	 * obtem o preco de venda do produto a partir do documento
	 * */
	private void getProductPrice(Document doc, Product product){

		Element content = doc.getElementById("content");
		Elements elements = content.getElementsByTag("p");
		for (Element e : elements) {
			String key = e.attr("class");
			if(key.equals("sales-price")){
				String value = e.text();
				product.setPrice(value);
			}
		}
	}
}
