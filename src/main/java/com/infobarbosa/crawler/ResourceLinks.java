package com.infobarbosa.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Obtem a lista de links a partir de um determinado documento.
 */
public class ResourceLinks {

    public ArrayList<String> list(Document doc) throws IOException {
        ArrayList<String> links = new ArrayList<String>();

        Elements pageLinks = doc.select("a[href]");

        for (Element link : pageLinks) {
            links.add( link.attr("abs:href") );
        }

        return links;
    }
}
