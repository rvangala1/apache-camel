package com.cap.bookstore.routes;

import com.cap.bookstore.processor.BookCacheProcessor;
import org.apache.camel.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddBookCacheRoute extends AbstractBookRoute {

    @Autowired
    BookCacheProcessor bookCacheProcessor;

    @Override
    public void configure() throws Exception {
        initializeExceptionHandler();



        from("direct:addBookCacheRoute")
                .routeId("addBookCacheRoute")
                .bean(bookCacheProcessor, "saveBookToCache")
        .end();
    }
}
