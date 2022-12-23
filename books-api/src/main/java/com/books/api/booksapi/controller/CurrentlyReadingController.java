package com.books.api.booksapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.books.api.booksapi.persistence.CurrentlyReadingDAO;

import com.books.api.booksapi.model.CurrentlyReading;

@RestController
@RequestMapping("currently-reading")
public class CurrentlyReadingController {
    private static final Logger LOG = Logger.getLogger(CurrentlyReadingController.class.getName());
    private CurrentlyReadingDAO currentlyReadingDAO;

    public CurrentlyReadingController(CurrentlyReadingDAO currentlyReadingDAO) {
        this.currentlyReadingDAO = currentlyReadingDAO;
    }

    @GetMapping("/{title}")
    public ResponseEntity<CurrentlyReading> getBook(@PathVariable String title) {
        LOG.info("GET /books/" + title);
        try {
            CurrentlyReading book = currentlyReadingDAO.getBook(title);
            if (book != null)
                return new ResponseEntity<CurrentlyReading>(book,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<CurrentlyReading[]> getBooks() {
        LOG.info("GET /books");

        try {
            CurrentlyReading[] bookArrayList = currentlyReadingDAO.getBooks();
            if (bookArrayList != null)
                return new ResponseEntity<CurrentlyReading[]>(bookArrayList,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<CurrentlyReading[]> findBook(@RequestParam String title) {
        LOG.info("GET /books/?title=" + title);
        try {
            CurrentlyReading[] books = currentlyReadingDAO.findBook(title);
            return new ResponseEntity<CurrentlyReading[]>(books,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<CurrentlyReading> createBook(@RequestBody CurrentlyReading book) {
        LOG.info("POST /book " + book);

        try {
            // if the book does not already exist
            if (currentlyReadingDAO.getBook(book.getTitle()) == null) {
                // then create a new one
                CurrentlyReading c = currentlyReadingDAO.createBook(book);
                return new ResponseEntity<CurrentlyReading>(c,HttpStatus.CREATED);
            }
            else { // if the book does exist
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{title}")
    public ResponseEntity<CurrentlyReading> updateBook(@PathVariable String title, @RequestBody CurrentlyReading book) {
        LOG.info("PUT /books/" + title + "/" + book);

        try {
            CurrentlyReading c = currentlyReadingDAO.updateBook(title, book);
            if (c != null)
                return new ResponseEntity<CurrentlyReading>(c,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<CurrentlyReading> deleteBook(@PathVariable String title) {
        LOG.info("DELETE /book/" + title);
        try {
            if (currentlyReadingDAO.deleteBook(title)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
