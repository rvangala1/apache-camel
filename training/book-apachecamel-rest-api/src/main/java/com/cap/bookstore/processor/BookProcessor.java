package com.cap.bookstore.processor;


import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import com.cap.bookstore.exception.DataException;
import com.cap.bookstore.exception.ResourceNotFoundException;
import com.cap.bookstore.model.BookAsyncResponse;
import com.cap.bookstore.model.BookResponse;
import com.cap.bookstore.repository.BookRepository;
import com.cap.bookstore.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
//import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("bookProcessor")
public class BookProcessor {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(Exchange exchange) throws JsonProcessingException {
        List<Book> books =new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            books.add(book);
        }
        exchange.getIn().setHeader("Content-Type", "application/json");
        return books;
    }

    public Book getBook(@Header("isbn") String isbn)
    {
        Book book = bookRepository.findOne(isbn);
             return book;
    }

    public BookResponse addBook(@Body Book book,Exchange exchange) {
        String isbn = exchange.getProperty("isbn", String.class);
        Book book1 = bookRepository.findOne(isbn);
        BookResponse bookResponse = new BookResponse();
        if(book1 != null) {
            throw new DataException("Book alreay exists");
        }
        else {
            bookRepository.save(book);
            bookResponse.setIsbn(book.getIsbn());
            bookResponse.setStatusCode("200");
            bookResponse.setStatus("added");
            exchange.setProperty("propIsAdded", true);
        }
        return bookResponse;
    }

    public BookResponse updateBook(@Header("isbn") String isbn,@Body Book bookNew, Exchange exchange) {

        Book book1 = bookRepository.findOne(isbn);
        BookResponse bookResponse = new BookResponse();
        if(book1 != null) {
            Book book = book1;
            book.setAuthor(bookNew.getAuthor());
            book.setTitle(bookNew.getTitle());
            book.setPublisher(bookNew.getPublisher());
            bookRepository.save(book);
            bookResponse.setIsbn(isbn);
            bookResponse.setStatusCode("200");
            bookResponse.setStatus("updated");
            exchange.setProperty("propIsUpdate", true);
        }
        else {
            throw new DataException("Book not exists");
        }
        return bookResponse;
    }

    public BookResponse deleteBook(@Header("isbn") String isbn) {
        Book book1 = bookRepository.findOne(isbn);
        if(book1 != null) {
            Book book = book1;
            bookRepository.delete(book);
            BookResponse bookResponse = new BookResponse();
            bookResponse.setIsbn(isbn);
            bookResponse.setStatusCode("200");
            bookResponse.setStatus("deleted");
            return bookResponse;
        } else
            throw new ResourceNotFoundException("No book found with ISBN ".concat(isbn));
    }
    public void deleteBook1(Exchange exchange) {
        String isbn = exchange.getProperty("propIsbn",String.class);
        Book book1 = bookRepository.findOne(isbn);
        if(book1 != null) {
            Book book = book1;
            bookRepository.delete(book);
            BookResponse bookResponse = new BookResponse();
            bookResponse.setIsbn(isbn);
            bookResponse.setStatusCode("200");
            bookResponse.setStatus("deleted");
            exchange.getIn().setBody(bookResponse);
        } else
            throw new ResourceNotFoundException("No book found with ISBN ".concat(isbn));
    }

    public void asyncResponse(Exchange exchange) {
        String isbn = exchange.getProperty("propIsbn",String.class);
        String transactionId = exchange.getProperty("transactionId",String.class);
        BookAsyncResponse bookAsyncResponse = new BookAsyncResponse();
        bookAsyncResponse.setIsbn(isbn);
        bookAsyncResponse.setTransactionId(transactionId);
        bookAsyncResponse.setStatus("Request for deletion received");
        exchange.getIn().setBody(bookAsyncResponse);
        }
    }


