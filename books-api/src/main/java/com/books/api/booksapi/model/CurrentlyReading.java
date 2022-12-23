package com.books.api.booksapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

enum Medium {
    PAPERBACK,
    ONLINE,
    HARDCOVER
}

public class CurrentlyReading extends Book {
    @JsonProperty("medium") private Medium medium;
    @JsonProperty("current") private int currentPage;
    @JsonProperty("total") private int totalPages;
    @JsonProperty("start") private double startDate;
    @JsonProperty("last") private double lastReadDate;

    public CurrentlyReading(@JsonProperty("title") String title, @JsonProperty("author") String author, 
        @JsonProperty("genre") Genre genre, @JsonProperty("medium") Medium medium, @JsonProperty("current") int currentPage,
        @JsonProperty("total") int totalPages, @JsonProperty("start") double startDate, 
        @JsonProperty("last") double lastReadDate) {
        super(title, author, genre);
    }
    
    public int getCurrentPage() {
        return currentPage;
    }

    public double getLastReadDate() {
        return lastReadDate;
    }

    public Medium getMedium() {
        return medium;
    }

    public double getStartDate() {
        return startDate;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setLastReadDate(double lastReadDate) {
        this.lastReadDate = lastReadDate;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public void setStartDate(double startDate) {
        this.startDate = startDate;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
