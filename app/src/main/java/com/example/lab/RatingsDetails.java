package com.example.lab;

public class RatingsDetails {
    String url ;
    String Ratings;

    public RatingsDetails(String url, String ratings) {
        this.url = url;
        Ratings = ratings;
    }

    public RatingsDetails() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }
}
