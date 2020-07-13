package com.cap.bookstore.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class ErrorEntity {

        @Id
        String exceptionUuid;
       // @GeneratedValue(strategy = GenerationType.AUTO)
       //@Column(name = "exceptionId", updatable = false, nullable = false)
       // Integer exceptionId;
        Integer status;
        String statusMessage;

     }


