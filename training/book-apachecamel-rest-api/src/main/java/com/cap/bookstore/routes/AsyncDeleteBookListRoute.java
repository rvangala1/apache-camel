package com.cap.bookstore.routes;

import com.cap.bookstore.model.BookResponse;
import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.LoggingLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsyncDeleteBookListRoute extends AbstractBookRoute {

    @Autowired
    BookProcessor bookProcessor;

    @Override
    public void configure() throws Exception {

        //initializeExceptionHandler();

        from("direct:asyncDeleteBookListRoute")
                .routeId("asyncDeleteBookListRoute")
                .setProperty("transactionId",header("transactionId"))
                .setProperty("propIsbn",header("isbnList"))
                .split(header("isbnList").tokenize(","))
                    .log(LoggingLevel.INFO, "${body}")
                    .setHeader("isbn",body())
                    .wireTap("direct:deleteBookRoute").end()
                   // .to("direct:deleteBookRoute")
                .end()
                .bean(bookProcessor,"asyncResponse")
        .end();
    }
}
