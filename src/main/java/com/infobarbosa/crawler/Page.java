package com.infobarbosa.crawler;

import java.util.*;

public class Page {

    private String url;
    private Set<String> links;

    public Page(){
        url = null;
        links = new HashSet<String>();
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

    @Override
    public String toString() {
        return "Page{" +
                "url='" + url + '\'' +
                ", links=" + Arrays.toString(links.toArray()) +
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
