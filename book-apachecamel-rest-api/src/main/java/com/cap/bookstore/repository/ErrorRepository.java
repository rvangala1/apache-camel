package com.cap.bookstore.repository;

import com.cap.bookstore.model.ErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<ErrorEntity, String> {


    //getallBooks
    //getBook details given by ISbn
    // Updated the book details
    //Delete the book (string Id)



}

