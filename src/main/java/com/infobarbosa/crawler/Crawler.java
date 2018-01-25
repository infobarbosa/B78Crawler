package com.infobarbosa.crawler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler {
	
	private static Logger logger = LoggerFactory.getLogger( Crawler.class );

	private static Crawler crawler = null;
	private static CrawlerBrokerKafka kafka = null;

	/**
	 * Construtor
	 * */
	private Crawler(){
		kafka = CrawlerBrokerKafka.getInstance();
	}

	/**
	 * Implement the singleton pattern
	 * */
	public static Crawler getInstance(){
		if(crawler == null)
			crawler = new Crawler();

		return crawler;
	}

	/**
	 * crawl sem argumentos (obtem o proximo url de uma fila)
	 * */
	public void crawl(){
		String url;
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

			Page page = new Page();
			page.setUrl(url);
			getLinks(doc, page);
			getProduct(doc, page);

			System.out.println(page);
		} catch(Exception e){
			logger.error( e.toString() );
		}
	}

	/**
	 * Obtem a lista de links do documento e adiciona aa lista da web page.
	 * */
	private void getLinks(Document doc, Page page){

		try {
			URI uri = new URI(doc.baseUri());
			System.out.println("URI(root): " + uri.getHost());

			PageUtils pageUtils = PageUtils.getInstance();
			ArrayList<String> links = pageUtils.list(doc);

			for(String link: links) {
				page.addLink(link);
				kafka.enqueue(link);
			}

		}catch(URISyntaxException se){
			se.printStackTrace();
		}
	}

	/**
	 * Obtem os dados do produto a partir do documento e atualiza a variavel page
	 * */
	private void getProduct(Document doc, Page page){

		Product product = new Product();
		product.setUrl(doc.location());

		getProductPrice(doc, product);
		getProductName(doc, product);
		getProductID(doc, product);

		System.out.println(product);
		page.setProduct(product);
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

	/**
	 * the main method
	 * */
	public static void main(String args[]){

		Crawler crawler = new Crawler();
		crawler.crawl();
	}
}
