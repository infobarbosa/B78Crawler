package com.infobarbosa;

import java.net.URL;
import java.io.BufferedReader;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
			}
		}catch(IOException e){
			logger.error( e.toString() );
		}catch(Exception e){
			logger.error( e.toString() );
			
		}

  }
}
