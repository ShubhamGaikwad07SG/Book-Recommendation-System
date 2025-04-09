package com.book.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.app.model.BookRequest;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, Integer> {
	
	List<BookRequest> findByUserId(int userId);

    List<BookRequest> findByStatus(String status);

}
