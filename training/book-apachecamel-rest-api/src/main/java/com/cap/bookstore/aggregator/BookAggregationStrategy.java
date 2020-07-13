package com.cap.bookstore.aggregator;

import com.cap.bookstore.model.BookResponse;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

public class BookAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {
            BookResponse newBookResponse = newExchange.getIn().getBody(BookResponse.class);
            List<BookResponse> bookResponseList = new ArrayList<BookResponse>();
            bookResponseList.add(newBookResponse);
            newExchange.getIn().setBody(bookResponseList);
            oldExchange = newExchange;
        }
        else {
            BookResponse newBookResponse = newExchange.getIn().getBody(BookResponse.class);
            List<BookResponse> bookResponseList = oldExchange.getIn().getBody(List.class);
            bookResponseList.add(newBookResponse);
            oldExchange.getIn().setBody(bookResponseList);
        }
        return oldExchange;
    }
}