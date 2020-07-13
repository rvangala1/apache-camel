package com.cap.bookstore.routes;

import com.cap.bookstore.exception.DataException;
import com.cap.bookstore.exception.ResourceNotFoundException;
import com.cap.bookstore.processor.BookCacheProcessor;
import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class GetBookByIsbnRoute extends AbstractBookRoute {

    @Autowired
    Environment environment;

    @Autowired
    private BookProcessor bookProcessor;

    @Autowired
    private BookCacheProcessor bookCacheProcessor;

    @Override
    public void configure() throws Exception {

        initializeExceptionHandler();

        Predicate isIsbn = header("isbn").isNotNull();
        Predicate isEmptyBody = body().isNull();
        Predicate isBodyExists = body().isNotNull();
        from("direct:getBookByIsbnRoute")
              .routeId("getBookByIsbnRoute")
              .setProperty("isbn",header("isbn"))
              .bean(bookCacheProcessor,"getBookFromCache")
              .choice()
                   .when(isEmptyBody)
                      .bean(bookProcessor, "getBook")
                      .choice()
                           .when(isBodyExists)
                               .setProperty("propBookRequest",body())
                               .wireTap("direct:addBookCacheRoute")
                       .end()
              .end()
              .choice()
                   .when(isEmptyBody)
                      .throwException(DataException.class,"No book found with ISBN ${header.isbn}")
              .end()
        .end();

    }
}
