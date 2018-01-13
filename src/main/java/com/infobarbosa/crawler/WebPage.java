package com.infobarbosa.crawler;

import java.util.Arrays;
import java.util.Objects;

public class WebPage {

    private String url;
    private String[] links;
    private Product product;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
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
                ", links=" + Arrays.toString(links) +
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
}
