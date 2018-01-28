package com.infobarbosa.crawler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


public class Crawler{
	
	private static Logger logger = LoggerFactory.getLogger( Crawler.class );

	private PageRepository pageRepo;

	@Inject
	public Crawler(PageRepository pageRepo){
		this.pageRepo = pageRepo;
	}

	/**
	 * obtem os dados a partir do URL fornecido
	 * */
	public void crawl(String url){
		try
		{
			if( !pageRepo.checkThePageWasCrawledAlready(url) ) {
				Document doc = Jsoup.connect(url).get();

				Page page = new Page();
				page.setUrl(url);
				getLinks(doc, page);
				logger.info("links obtidos");
				pageRepo.addPage(page);

				logger.debug(page.toString());
			}else{
				logger.info("Crawled URL. " + url);
			}

		} catch(Exception e){
			logger.error( e.toString() );
		} finally{
			pageRepo.destroy();
		}
	}

	/**
	 * Obtem a lista de links do documento e adiciona aa lista da web page.
	 * */
	private void getLinks(Document doc, Page page){

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
	 * the main method
	 * */
	public static void main(String args[]){
		if(args.length == 0)
			throw new RuntimeException("Obrigatorio informar uma url");

		String url = args[0];

		Injector injector = Guice.createInjector(new CrawlerInjector());

		Crawler crawler = injector.getInstance(Crawler.class);

		crawler.crawl(url);
		logger.info("finalizando...");
	}
}
