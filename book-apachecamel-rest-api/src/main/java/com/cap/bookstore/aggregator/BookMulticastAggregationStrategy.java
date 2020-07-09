package com.cap.bookstore.aggregator;

import com.cap.bookstore.model.BookResponse;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

public class BookMulticastAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {
            oldExchange = newExchange;
        }
        else {
            BookResponse newBookResponse = newExchange.getIn().getBody(BookResponse.class);
            if (newBookResponse != null)
                oldExchange.getIn().setBody(newBookResponse);
        }
        setSuccessHeaders(oldExchange);
        return oldExchange;
    }

    public void setSuccessHeaders(Exchange exchange) {
        exchange.getIn().removeHeaders("*");
        exchange.getIn().setHeader("CamelHttpResponseCode", HttpStatus.OK.value());
        exchange.getIn().setHeader("CamelHttpResponseText", HttpStatus.OK.getReasonPhrase());
    }
}