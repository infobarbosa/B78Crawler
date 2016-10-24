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

public class Main{
	
  private static Logger logger = LoggerFactory.getLogger( Main.class );

  URL url = null;

  public Main(String url)
  {
    try
    {
      this.url = new URL( url );
    }catch(MalformedURLException e)
    {
      logger.error( e.getMessage() );
    }
  }

  public Main(URL url)
  {
    this.url = url;
  }

  public void setUrl(URL url){
    this.url = url;
  }

  public void setUrl(String url){
    try
    {
      this.url = new URL( url );
    }catch(MalformedURLException e)
    {
      logger.error( e.getMessage() );
    }
  }

	public static void main(String args[]){
    //TODO
	}

  public void craw(){
    try
    {
      URLConnection urlConnection = url.openConnection();
      urlConnection.setConnectTimeout(15000);
      HttpURLConnection connection = null;

      if(urlConnection instanceof HttpURLConnection){
        connection = (HttpURLConnection) urlConnection;
      }else{
        throw new Exception("Please enter an HTTP URL.");
      }

      BufferedReader in = new BufferedReader(
        new InputStreamReader( connection.getInputStream() )
      );
      String urlString = "";
      String current;
      while((current = in.readLine()) != null)
      {
          urlString += current;
      }

      logger.debug( urlString );
    }catch(IOException e)
    {
      logger.error( e.toString() );
    }catch(Exception e)
    {
      logger.error( e.toString() );
    }

  }
}
