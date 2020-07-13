package com.cap.bookstore.routes;

import com.cap.bookstore.model.ErrorResponse;
import com.cap.bookstore.processor.ErrorProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionProcessingRoute extends RouteBuilder {

    @Autowired
    ErrorProcessor errorProcessor;

    @Override
    public void configure() throws Exception {

    GsonDataFormat error = new GsonDataFormat(ErrorResponse.class);

    from("activemq:queue:errorBookQueue?concurrentConsumers=1")
            .unmarshal(error)
            .log("${body}")
            .bean(errorProcessor,"insertError")
    .end();
    }
}
