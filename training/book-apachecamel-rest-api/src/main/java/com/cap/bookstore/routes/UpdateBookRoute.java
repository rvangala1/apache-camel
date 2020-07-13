package com.cap.bookstore.routes;

import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UpdateBookRoute extends AbstractBookRoute {

    @Autowired
    private BookProcessor bookProcessor;

    @Override
    public void configure() throws Exception {

        initializeExceptionHandler();

        from("direct:updateBookRoute")
            .routeId("updateBookRoute")
            .setProperty("propBookRequest",body())
            .bean(bookProcessor,"updateBook" )
            //.choice().when(exchangeProperty("propIsUpdate"))
                .wireTap("direct:addBookCacheRoute")
            //.end()
        .end();
    }
}

