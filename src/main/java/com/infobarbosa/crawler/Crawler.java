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
	private static PageRepository pageRepo = PageRepositoryCassandra.getInstance();


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
//	public void crawl(){
//		String url;
//		while(true) {
//			url = kafka.dequeueNextPageUrl();
//			crawl(url);
//		}
//	}

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
			logger.info("links obtidos");
			getProduct(doc, page);
			logger.debug("informacoes de produto obtidas");
			pageRepo.addPageUrl(url);

			System.out.println(page);
		} catch(Exception e){
			logger.error( e.toString() );
		}
	}

	/**
	 * Obtem a lista de links do documento e adiciona aa lista da web page.
	 * */
	public void getLinks(Document doc, Page page){

		logger.debug("obtendo links.");
		try {
			URI uri = new URI(doc.baseUri());
			logger.info("URI(root): " + uri.getHost());

			PageUtils pageUtils = PageUtils.getInstance();
			ArrayList<String> links = pageUtils.list(doc);

			for(String link: links) {
				page.addLink(link);
			}

		}catch(URISyntaxException se){
			logger.error("Erro!", se);
		}
	}

	/**
	 * Obtem os dados do produto a partir do documento e atualiza a variavel page
	 * */
	public void getProduct(Document doc, Page page){
		logger.debug("obtendo dados do produto");

		Element content = null;
		try{

			content = doc.getElementById("content");
			if(content == null)
				throw new Exception("esta pagina nao possui elemento content");

			Product product = new Product();
			product.setUrl(doc.location());

			getProductPrice(content, product);
			getProductName(content, product);
			getProductID(content, product);

			logger.debug(product.toString());
			page.setProduct(product);
		}catch(Exception e){
			logger.error("obtendo dados do produto. ", e);
		}
	}


	/**
	 * Obtem o id do produto
	 * */
	private void getProductID(Element content, Product product){
		logger.debug("obtento id do produto");

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
	private void getProductName(Element content, Product product){
		logger.debug("obtendo nome do produto");

		try{

			Elements elements = content.getElementsByTag("h1");

			if(elements == null)
				throw new Exception("variaval element nao contem nos filhos.");

			for (Element e : elements) {
				String key = e.attr("class");
				if(key.equals("product-name")){
					String value = e.text();
					product.setDescription(value);
				}
			}
		}catch(Exception e){
			logger.error("Obtendo nome. ", e);
		}
	}

	/**
	 * obtem o preco de venda do produto a partir do documento
	 * */
	private void getProductPrice(Element content, Product product){
		logger.debug("obtendo preco do produto. " + product);

		try {

			Elements elements = content.getElementsByTag("p");

			if(elements == null)
				throw new Exception("variaval element nao contem nos filhos.");

			for (Element e : elements) {
				String key = e.attr("class");
				if (key.equals("sales-price")) {
					String value = e.text();
					product.setPrice(value);
				}
			}
		}catch(Exception e){
			logger.error("Obtendo preco. ", e);
		}

		logger.debug("preco obtido");
	}

	/**
	 * the main method
	 * */
	public static void main(String args[]){
		if(args.length == 0)
			throw new RuntimeException("Obrigatorio informar uma url");

		String url = args[0];

		Crawler crawler = new Crawler();
		crawler.crawl(url);
	}
}
