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

import com.books.api.booksapi.persistence.ToReadDAO;
import com.books.api.booksapi.model.ToRead;

@RestController
@RequestMapping("to-read")
public class ToReadController {
    private static final Logger LOG = Logger.getLogger(CurrentlyReadingController.class.getName());
    private ToReadDAO toReadDAO;

    public ToReadController(ToReadDAO toReadDAO) {
        this.toReadDAO = toReadDAO;
    }

    @GetMapping("/{title}")
    public ResponseEntity<ToRead> getBook(@PathVariable String title) {
        LOG.info("GET /books/" + title);
        try {
            ToRead book = toReadDAO.getBook(title);
            if (book != null)
                return new ResponseEntity<ToRead>(book,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<ToRead[]> getBooks() {
        LOG.info("GET /books");

        try {
            ToRead[] bookArrayList = toReadDAO.getBooks();
            if (bookArrayList != null)
                return new ResponseEntity<ToRead[]>(bookArrayList,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ToRead[]> findBook(@RequestParam String title) {
        LOG.info("GET /books/?title=" + title);
        try {
            ToRead[] books = toReadDAO.findBook(title);
            return new ResponseEntity<ToRead[]>(books,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<ToRead> createBook(@RequestBody ToRead book) {
        LOG.info("POST /book " + book);

        try {
            // if the book does not already exist
            if (toReadDAO.getBook(book.getTitle()) == null) {
                // then create a new one
                ToRead c = toReadDAO.createBook(book);
                return new ResponseEntity<ToRead>(c,HttpStatus.CREATED);
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
    public ResponseEntity<ToRead> updateBook(@PathVariable String title, @RequestBody ToRead book) {
        LOG.info("PUT /books/" + title + "/" + book);

        try {
            ToRead c = toReadDAO.updateBook(title, book);
            if (c != null)
                return new ResponseEntity<ToRead>(c,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<ToRead> deleteBook(@PathVariable String title) {
        LOG.info("DELETE /book/" + title);
        try {
            if (toReadDAO.deleteBook(title)){
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
