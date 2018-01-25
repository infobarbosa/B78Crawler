package com.infobarbosa.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Obtem a lista de links a partir de um determinado documento.
 */
public class PageUtils {

    private static PageUtils pageUtils = null;

    private PageUtils(){}

    /**
     * return a singleton
     * */
    public static PageUtils getInstance(){
        if(pageUtils == null)
            pageUtils = new PageUtils();

        return pageUtils;
    }

    /**
     * produz uma lista de links obtidos a partir de um documento
     * */
    public ArrayList<String> list(Document doc){
        ArrayList<String> links = new ArrayList<String>();

        Elements pageLinks = doc.select("a[href]");

        for (Element link : pageLinks) {
            links.add( link.attr("abs:href") );
        }

        return links;
    }
}
