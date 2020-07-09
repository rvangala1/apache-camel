package com.cap.bookstore.routes;

import com.cap.bookstore.processor.BookProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DeleteBookWireTapRoute extends AbstractBookRoute {

    @Autowired
    Environment environment;

    @Autowired
    private BookProcessor bookProcessor;

    @Override
    public void configure() throws Exception {

        initializeExceptionHandler();

        from("direct:deleteBookWireTapRoute")
                .routeId("deleteBookWireTapRoute")
                .setProperty("propIsbn",header("isbn"))
                .setProperty("transactionId",header("transactionId"))
                .wireTap("direct:deleteBookRoute")
                .bean(bookProcessor,"asyncResponse")
        .end();
    }
}

