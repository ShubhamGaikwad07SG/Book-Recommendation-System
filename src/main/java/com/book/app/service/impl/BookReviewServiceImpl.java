package com.book.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book.app.model.BookReview;
import com.book.app.repository.BookReviewRepository;
import com.book.app.service.BookReviewService;

@Service
public class BookReviewServiceImpl implements BookReviewService {

	 @Autowired
	    private BookReviewRepository bookReviewRepository;
	
	 
	@Override
	public void saveReview(BookReview review) {
		bookReviewRepository.save(review);
		
	}



	@Override
	public Page<BookReview> getReviewsByBookId(int id, Pageable pageable) {
		return bookReviewRepository.findByBookId(id, pageable);
	}

	
}
