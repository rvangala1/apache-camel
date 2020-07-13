package com.cap.bookstore.routes;

import com.cap.bookstore.exception.HttpExceptionHandler;
import com.cap.bookstore.exception.DataException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractBookRoute extends RouteBuilder {

    protected void initializeExceptionHandler() {

           onException(DataException.class)
                .log(LoggingLevel.ERROR,"Data exception error occurred: ${exception.stacktrace}")
                .handled(true)
                .bean(HttpExceptionHandler.class,"errorResponse")
                .to("activemq:queue:errorBookQueue");



           onException(Exception.class)
                .log(LoggingLevel.ERROR,"Exception error occurred: ${exception.stacktrace}")
                .handled(true)
                .bean(HttpExceptionHandler.class,"errorResponse")
                .to("activemq:queue:errorBookQueue")
           .end();

    }

}
