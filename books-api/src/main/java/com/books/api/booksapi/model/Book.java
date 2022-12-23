package com.books.api.booksapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

enum Genre {
    POLITICS,
    HISTORY,
    NON_FICTION,
    FICTION,
    SCIENCE_FICTION,
    PHILOSOPHY,
    FEMINISM,
    MYSTERY,
    ROMANCE,
    HUMOR,
    SUSPENSE
}

public class Book {
    @JsonProperty("title") private String title;
    @JsonProperty("author") private String author;
    @JsonProperty("genre") private Genre genre;
    @JsonProperty("pages") private int pages;

    public Book (@JsonProperty("title") String title, @JsonProperty("author") String author, 
        @JsonProperty("genre") Genre genre, @JsonProperty("pages") int pages) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
