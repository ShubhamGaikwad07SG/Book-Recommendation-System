package com.book.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.book.app.model.BookRequest;

import jakarta.transaction.Transactional;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, Integer> {
	
	List<BookRequest> findByUserId(int userId);

    List<BookRequest> findByStatus(String status);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM BookRequest br WHERE br.book.id = :bookId")
    void deleteByBookId(@Param("bookId") int bookId);

}
