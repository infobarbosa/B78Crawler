package com.infobarbosa.crawler;

import java.util.Objects;

public class Product {

    private String url; //a identificacao do produto depende da web page visitada
    private String id;
    private String description;
    private String price;

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getUrl(), product.getUrl()) &&
                Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUrl(), getId());
    }
}
