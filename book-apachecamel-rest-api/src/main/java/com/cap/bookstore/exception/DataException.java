package com.cap.bookstore.exception;


import org.apache.camel.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public class DataException extends RuntimeException {

    public DataException(String message) {
        super(message);
    }

    public void noBookException(Exchange exchange) {

        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
        exchange.getIn().setBody("No books available with given isbn ".concat(exchange.getIn().getHeader("isbn").toString()));
    }
}
