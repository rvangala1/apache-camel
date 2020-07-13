package com.cap.bookstore.model;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ErrorResponse {

    Integer status;
    String statusMessage;

   /* public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }*/
}
