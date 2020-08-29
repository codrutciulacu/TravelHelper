package com.codrut.travelhelper.data.model;


import java.util.Objects;

public class AdditionalInfo {
    private String title;
    private Float rating;
    private Integer price;

    private AdditionalInfo() {
    }

    public AdditionalInfo(String title, Float rating, Integer price) {
        this.title = title;
        this.rating = rating;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalInfo that = (AdditionalInfo) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rating, price);
    }

}
