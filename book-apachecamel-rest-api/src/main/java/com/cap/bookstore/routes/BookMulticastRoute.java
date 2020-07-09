package com.cap.bookstore.routes;

import com.cap.bookstore.aggregator.BookAggregationStrategy;
import com.cap.bookstore.aggregator.BookMulticastAggregationStrategy;
import com.cap.bookstore.exception.DataException;
import com.cap.bookstore.model.BookResponse;
import com.cap.bookstore.processor.BookProcessor;
import org.apache.camel.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BookMulticastRoute extends AbstractBookRoute {

    @Autowired
    Environment environment;

    @Autowired
    private BookProcessor bookProcessor;

    @Autowired
    BookResponse bookResponse;

    @Override
    public void configure() throws Exception {

        initializeExceptionHandler();

        from("direct:bookMulticastRoute")
                .routeId("bookMulticastRoute")
                .split(body(),new BookAggregationStrategy())//.parallelProcessing(true)//.stopOnException()
                    .setHeader("isbn",simple("${body.isbn}"))
                    .setProperty("isbn", simple("${body.isbn}"))
                     .multicast(new BookMulticastAggregationStrategy()).parallelProcessing(true)
                          .to("direct:addBooksRoute", "direct:updateBookRoute")
                     .end()
                .end()
        .end();
        }

}

