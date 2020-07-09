package com.cap.bookstore.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Component
public class BookResponse {

    String isbn;
    String statusCode;
    String status;

}

