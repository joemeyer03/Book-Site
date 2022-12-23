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

    public Book (@JsonProperty("title") String title, @JsonProperty("author") String author, 
        @JsonProperty("genre") Genre genre) {
            this.title = title;
            this.author = author;
            this.genre = genre;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
