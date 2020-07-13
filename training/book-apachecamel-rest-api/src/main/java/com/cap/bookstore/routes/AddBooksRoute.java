package com.cap.bookstore.routes;

import com.cap.bookstore.aggregator.BookAggregationStrategy;
import com.cap.bookstore.exception.DataException;
import com.cap.bookstore.model.BookResponse;
import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AddBooksRoute extends AbstractBookRoute {

    @Autowired
    Environment environment;

    @Autowired
    private BookProcessor bookProcessor;

    @Autowired
    BookResponse bookResponse;

    @Override
    public void configure() throws Exception {

        initializeExceptionHandler();

        Predicate P = exchangeProperty("propIsbn").isEqualTo("");

        from("direct:addBooksRoute")
                .routeId("addBooksRoute")
                   .setProperty("propBookRequest",body())
                   .bean(bookProcessor, "addBook")
                  // .choice().when(exchangeProperty("propIsAdded"))
                        .wireTap("direct:addBookCacheRoute")
                 //  .end()
        .end();
    }
}
