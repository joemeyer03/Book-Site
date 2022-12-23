package com.books.api.booksapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

enum Rating {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE
}

enum Status {
    READ,
    DNF
}

enum Medium {
    PAPERBACK,
    ONLINE,
    HARDCOVER
}

enum Recommend {
    YES,
    NO,
    CONDITIONALLY
}

public class HaveRead extends Book {
    @JsonProperty("status") private Status status;
    @JsonProperty("medium") private Medium medium;
    @JsonProperty("date") private int date;
    @JsonProperty("rating") private Rating rating;
    @JsonProperty("recommend") private Recommend recommend;
    @JsonProperty("thoughts") private String thoughts;

    public HaveRead(@JsonProperty("title") String title, @JsonProperty("author") String author, 
        @JsonProperty("genre") Genre genre, @JsonProperty("pages") int pages, @JsonProperty("status") Status status, 
        @JsonProperty("medium") Medium medium, @JsonProperty("date") int date, 
        @JsonProperty("rating") Rating rating, @JsonProperty("recommend") Recommend recommend, 
        @JsonProperty("thoughts") String thoughts) {
        super(title, author, genre, pages);
        this.status = status;
        this.medium = medium;
        this.date = date;
        this.rating = rating;
        this.recommend = recommend;
        this.thoughts = thoughts;
    }
    
    public int getDate() {
        return date;
    }

    public Medium getMedium() {
        return medium;
    }

    public Rating getRating() {
        return rating;
    }

    public Recommend getRecommend() {
        return recommend;
    }

    public Status getStatus() {
        return status;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setRecommend(Recommend recommend) {
        this.recommend = recommend;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }
}
