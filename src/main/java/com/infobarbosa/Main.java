package com.infobarbosa;

import java.net.URL;
import java.io.BufferedReader;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main{
	
  URL url = null;

  public setUrl(URL url){
    this.url = URL
  }

  public setUrl(String url){
    this.url = new URL( url );
  }

	public static void main(String args[]){

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
                System.out.println("Please enter an HTTP URL.");
                return;
          }

          BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
          );
        String urlString = "";
        String current;
        while((current = in.readLine()) != null)
        {
            urlString += current;
        }
        System.out.println(urlString);
        }catch(IOException e)
        {
        e.printStackTrace();
        }

  }
}
