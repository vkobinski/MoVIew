package com.kodev.moview.models;


public class Movie {

    // Classe que define um Filme no aplicativo

    private Long id;
    private String name;
    private boolean seen, watchlist;
    private int rating;
    private String apiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        if(seen == 1) this.seen = true;
        else this.seen = false;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public int getWatchlist() {
        if(this.watchlist == true) return 1;
        else return 0;
    }

    public int getSeen() {
        if(this.seen == true) return 1;
        else return 0;
    }

    public void setWatchlist(int watchlist) {
        if(watchlist == 1) this.watchlist = true;
        else this.watchlist = false;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if(rating > 5 || rating < 0) {
            throw new IllegalArgumentException("Rating must be in range 0 to 5");
        }
        this.rating = rating;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }
}
