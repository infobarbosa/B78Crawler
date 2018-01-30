package com.infobarbosa.crawler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


public class Crawler{
	
	private static Logger logger = LoggerFactory.getLogger( Crawler.class );
	private final PageMessageBus messageBus;
	private final PageRepository repository;

	@Inject
	public Crawler(PageRepository repository, PageMessageBus messageBus){

		this.repository = repository;
		this.messageBus = messageBus;
	}

	/**
	 * crawling com urls obtidos a partir de um servico
	 * */
	public void crawl(){

	    while(true) {
            List<String> urlList = messageBus.dequeueNextPageUrl();

            for (String url : urlList) {
                logger.debug("after dequeue: " + url);
                if (url != null) {
                    crawl(url);
                }
            }

            //TODO retirar o trecho abaixo do codigo final
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

	/**
	 * obtem os dados a partir do URL fornecido
	 * */
	public void crawl(String url){
		try
		{
			if( !repository.checkThePageWasCrawledAlready(url) ) {
				logger.debug("crawling " + url);
				Document doc = Jsoup.connect(url).get();

				Page page = new Page();
				page.setUrl(url);
				getLinks(doc, page);
				logger.info("links obtidos");
				repository.addPage(page);

				logger.debug(page.toString());
			}else{
				logger.info("Crawled URL. " + url);
			}

		} catch(Exception e){
			logger.error( e.toString() );
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

			links.forEach((link) -> {
				page.addLink(link);
				messageBus.enqueuePageUrl(link);
			});

		}catch(URISyntaxException se){
			logger.error("Erro!", se);
		}
	}

	/**
	 * libera os recursos alocados
	 * */
	public void destroy(){
		repository.destroy();
		messageBus.destroy();
	}

	/**
	 * the main method
	 * */
	public static void main(String args[]) {
		Injector injector = Guice.createInjector(new CrawlerInjector());

		Crawler crawler = injector.getInstance(Crawler.class);

		try {

			String url = null;

			if(args.length == 1)
				url = args[0];

			if (url == null || url.trim().equals(""))
				crawler.crawl();
			else
				crawler.crawl(url);

			logger.info("finalizando...");

		}finally {
			crawler.destroy();
		}
	}
}
