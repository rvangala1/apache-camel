package com.cap.bookstore.routes;



import com.cap.bookstore.model.Book;

import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



@Component
public class GetBooksRoute extends AbstractBookRoute {

    @Autowired
    Environment environment;

    @Autowired
    BookProcessor bookProcessor;

    @Override
    public void configure() throws Exception {

        initializeExceptionHandler();

        from("direct:getBooksRoute")
                .routeId("bookstore-getbooksroute")
                .bean(bookProcessor,"getAllBooks")
                .removeHeaders("*","Content-Type")
        .end();


    }
}
