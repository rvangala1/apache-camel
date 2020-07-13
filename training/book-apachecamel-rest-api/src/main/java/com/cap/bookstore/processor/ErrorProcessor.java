package com.cap.bookstore.processor;

import com.cap.bookstore.model.ErrorEntity;
import com.cap.bookstore.model.ErrorResponse;
import com.cap.bookstore.repository.ErrorRepository;
import org.apache.camel.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ErrorProcessor {

    @Autowired
    private ErrorRepository errorRepository;

    @Autowired
    ErrorEntity errorEntity;

    public void insertError(@Body ErrorResponse errorResponse) {
           // System.out.println(errorResponse.getStatus());
            Integer status = errorResponse.getStatus();
            errorEntity.setExceptionUuid(UUID.randomUUID().toString());
            errorEntity.setStatus(status);
            errorEntity.setStatusMessage(errorResponse.getStatusMessage());
            errorRepository.save(errorEntity);

        }
}
