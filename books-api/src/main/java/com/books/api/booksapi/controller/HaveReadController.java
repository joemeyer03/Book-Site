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

import com.books.api.booksapi.persistence.HaveReadDAO;

import com.books.api.booksapi.model.HaveRead;

@RestController
@RequestMapping("have-read")
public class HaveReadController {
    private static final Logger LOG = Logger.getLogger(HaveReadController.class.getName());
    private HaveReadDAO haveReadDAO;

    public HaveReadController(HaveReadDAO haveReadDAO) {
        this.haveReadDAO = haveReadDAO;
    }

    @GetMapping("/{title}")
    public ResponseEntity<HaveRead> getBook(@PathVariable String title) {
        LOG.info("GET /books/" + title);
        try {
            HaveRead book = haveReadDAO.getBook(title);
            if (book != null)
                return new ResponseEntity<HaveRead>(book,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<HaveRead[]> getBooks() {
        LOG.info("GET /books");

        try {
            HaveRead[] bookArrayList = haveReadDAO.getBooks();
            if (bookArrayList != null)
                return new ResponseEntity<HaveRead[]>(bookArrayList,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<HaveRead[]> findBook(@RequestParam String title) {
        LOG.info("GET /books/?title=" + title);
        try {
            HaveRead[] books = haveReadDAO.findBook(title);
            return new ResponseEntity<HaveRead[]>(books,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<HaveRead> createBook(@RequestBody HaveRead book) {
        LOG.info("POST /book " + book);

        try {
            // if the book does not already exist
            if (haveReadDAO.getBook(book.getTitle()) == null) {
                // then create a new one
                HaveRead c = haveReadDAO.createBook(book);
                return new ResponseEntity<HaveRead>(c,HttpStatus.CREATED);
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
    public ResponseEntity<HaveRead> updateBook(@PathVariable String title, @RequestBody HaveRead book) {
        LOG.info("PUT /books/" + title + "/" + book);

        try {
            HaveRead c = haveReadDAO.updateBook(title, book);
            if (c != null)
                return new ResponseEntity<HaveRead>(c,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<HaveRead> deleteBook(@PathVariable String title) {
        LOG.info("DELETE /book/" + title);
        try {
            if (haveReadDAO.deleteBook(title)){
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
