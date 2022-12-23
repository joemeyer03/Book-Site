package com.books.api.booksapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

enum Interest {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE
}

public class ToRead extends Book {
    @JsonProperty("price") private double price;
    @JsonProperty("owned") private boolean owned;
    @JsonProperty("interest") private Interest interest;
    @JsonProperty("description") private String description;

    public ToRead(@JsonProperty("title") String title, @JsonProperty("author") String author, 
        @JsonProperty("genre") Genre genre, @JsonProperty("pages") int pages, @JsonProperty("price") double price, 
        @JsonProperty("owned") boolean owned, @JsonProperty("interest") Interest interest, 
        @JsonProperty("description") String description) {
        super(title, author, genre, pages);
        this.price = price;
        this.owned = owned;
        this.interest = interest;
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }

    public boolean getOwned() {
        return owned;
    }

    public Interest getInterest() {
        return interest;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
