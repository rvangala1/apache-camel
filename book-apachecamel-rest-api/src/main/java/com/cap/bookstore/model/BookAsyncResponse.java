package com.cap.bookstore.model;

import lombok.*;
import org.springframework.stereotype.Component;

    @Data
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor

    @Component
    public class BookAsyncResponse {

        String isbn;
        String transactionId;
        String status;

    }

