package com.book.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.book.app.model.BookReview;

public interface BookReviewService {

	public void saveReview(BookReview review);
	

	public Page<BookReview> getReviewsByBookId(int id, Pageable pageable);
}
