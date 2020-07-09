package com.cap.bookstore.exception;


import com.cap.bookstore.model.BookResponse;
import com.cap.bookstore.model.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HttpExceptionHandler {
    private ErrorResponse errorResponse;

    public void errorResponse(Exchange exchange) throws JsonProcessingException {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        String isbn = exchange.getProperty("isbn", String.class);

        if (cause instanceof ResourceNotFoundException) {
            ResourceNotFoundException validationEx = (ResourceNotFoundException) cause;
            errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), validationEx.getMessage());
        }
        else if (cause instanceof DataException){
            DataException validationEx = (DataException) cause;
            errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),validationEx.getMessage() );
        }
        else {
            Exception validationEx = (Exception) cause;
            errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), validationEx.getMessage());
        }

        exchange.getIn().removeHeaders("*");
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, errorResponse.getStatus());
        exchange.getIn().setBody(new ObjectMapper().writeValueAsString(errorResponse));
    }
}

