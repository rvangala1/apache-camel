package com.cap.bookstore.routes;

import com.cap.bookstore.exception.DataException;
import com.cap.bookstore.exception.HttpExceptionHandler;
import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DeleteBookRoute extends AbstractBookRoute {

    @Autowired
    Environment environment;

    @Autowired
    private BookProcessor bookProcessor;

    @Override
    public void configure() throws Exception {

        initializeExceptionHandler();

         from("direct:deleteBookRoute")
             .routeId("deleteBookRoute")
             .setProperty("propIsbn",header("isbn"))
             .bean(bookProcessor,"deleteBook1")
        .end();
    }
}
