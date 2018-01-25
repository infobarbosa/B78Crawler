package com.infobarbosa.crawler;

import java.util.*;

public class Page {

    private String url;
    private Set<String> links;
    private Product product;

    public Page(){
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
        return "Page{" +
                "url='" + url + '\'' +
                ", links=" + Arrays.toString(links.toArray()) +
                ", product=" + product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(getUrl(), page.getUrl());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUrl());
    }

    public void addLink(String link) {
        links.add(link);
    }
}
