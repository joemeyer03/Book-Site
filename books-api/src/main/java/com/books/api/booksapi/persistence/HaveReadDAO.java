package com.books.api.booksapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.books.api.booksapi.model.HaveRead;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HaveReadDAO {
    private static final Logger LOG = Logger.getLogger(CurrentlyReadingDAO.class.getName());
    // local cache of all the products. takes in the name and gives out the product object
    Map<String,HaveRead> books;
    // converts between product objects and JSON text format
    private ObjectMapper objectMapper; 
    // name to read and write to
    private String filename;

    /**
     * Constructor for currently reading DAO
     * @param filename
     * @param objectMapper
     * @throws IOException
     */
    public HaveReadDAO(@Value("${have-read.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the products from the file
    }

    /**
     * Returns an array of all books
     * @return array of all books
     * @throws IOException when file cannot be accessed or written to
     */
    public HaveRead[] getBooks() throws IOException {
        ArrayList<HaveRead> bookArrayList = new ArrayList<>();

        for (HaveRead book : books.values()) {
            bookArrayList.add(book);
        }
        Collections.reverse(bookArrayList);
        HaveRead[] bookArray = new HaveRead[bookArrayList.size()];
        bookArrayList.toArray(bookArray);
        return bookArray;
    }

    /**
     * Returns array of books containing the search result in the title or author
     * @param search books with search will be returned
     * @return array of books with specified name, or contain specified name
     * @throws IOException when file cannot be accessed or written to
    */
    public HaveRead[] findBook(String search) throws IOException {
        if (search == null) return null;
        ArrayList<HaveRead> BooksArrayList = new ArrayList<>();

        for (HaveRead book : books.values()) {
            if (book.getTitle().toLowerCase().contains(search) || book.getAuthor().toLowerCase().contains(search)) {
                BooksArrayList.add(book);
            }
        }

        HaveRead[] BooksArray = new HaveRead[BooksArrayList.size()];
        BooksArrayList.toArray(BooksArray);
        return BooksArray;
    }

    public HaveRead createBook(HaveRead book) throws IOException {
        synchronized(books) {
            // We create a new book object
            HaveRead newBook = new HaveRead(book.getTitle(), book.getAuthor(), book.getGenre(), book.getPages(), 
                book.getStatus(), book.getMedium(), book.getDate(), book.getRating(), book.getRecommend(), book.getThoughts());
            books.put(book.getTitle(),newBook);
            save(); // may throw an IOException
            return newBook;
        }
    }

    public HaveRead getBook(String title) throws IOException {
        HaveRead book = books.get(title);
        return book;
    }

    /**
     * Saves the products from the map into the file
     * @return true if written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        HaveRead[] BooksArray = getBooks();   // Gets all products

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),BooksArray);
        return true;
    }

    /**
     * Loads the products from the JSON file into the map
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed
     */
    private boolean load() throws IOException {
        books = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of heroes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        HaveRead[] BooksArray = objectMapper.readValue(new File(filename),HaveRead[].class);

        // Add each book to the tree map 
        for (HaveRead book : BooksArray) {
            books.put(book.getTitle(),book);
        }
        return true;
    }

    /**
     * Deletes a book with given name
     * @return true if product exists and is deleted; false if product was not found with given name
     * @throws IOException when file cannot be accessed
     */
    public boolean deleteBook(String title) throws IOException {
        synchronized(books) {
            if (books.containsKey(title)) {
                books.remove(title);
                return save();
            }
            else return false;
        }
    }

    public HaveRead updateBook(String title, HaveRead book) throws IOException {
        synchronized(books) {
            if (books.containsKey(title) == false)
                return null;  // product does not exist
            books.remove(title);
            books.put(book.getTitle(),book);
            save(); // may throw an IOException
            return book;
        }
    }
}
