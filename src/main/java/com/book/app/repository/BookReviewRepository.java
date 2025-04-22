package com.book.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.book.app.model.BookReview;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {
    Page<BookReview> findByBookId(int bookId, Pageable pageable);
}


