package com.infobarbosa.crawler;

import java.util.*;

public class WebPage {

    private String url;
    private Set<String> links;
    private Product product;

    public WebPage(){
        url = null;
        links = new HashSet<String>();
        product = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getLinks() {
        return links;
    }

    public void setLinks(Set<String> links) {
        this.links = links;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "url='" + url + '\'' +
                ", links=" + Arrays.toString(links.toArray()) +
                ", product=" + product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebPage webPage = (WebPage) o;
        return Objects.equals(getUrl(), webPage.getUrl());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUrl());
    }

    public void addLink(String link) {
        links.add(link);
    }
}
