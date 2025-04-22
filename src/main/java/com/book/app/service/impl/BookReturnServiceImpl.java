package com.book.app.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.app.model.Book;
import com.book.app.model.BookRequest;
import com.book.app.model.BookReturn;
import com.book.app.model.User;
import com.book.app.repository.BookRepository;
import com.book.app.repository.BookReturnRepository;
import com.book.app.repository.UserRepository;
import com.book.app.service.BookReturnService;

import jakarta.transaction.Transactional;
@Service
public class BookReturnServiceImpl implements BookReturnService {

	 @Autowired
	 private BookReturnRepository returnBookRepo;
	 
	 @Autowired
	 private UserRepository userRepo;
	 
	 @Autowired
	 private BookRepository bookRepo;
	 
	 
	 
	@Override
	public BookReturn sendReturnRequest(int userId, int bookId) {
			        
	        Optional<User> userOpt = userRepo.findById(userId);
	        Optional<Book> bookOpt = bookRepo.findById(bookId);

	        if (userOpt.isPresent() && bookOpt.isPresent()) {
	            BookReturn ret = new BookReturn();
	            ret.setUser(userOpt.get());
	            ret.setBook(bookOpt.get());
	            ret.setReturnDate(LocalDate.now());
	            ret.setStatus("PENDING");
	           return returnBookRepo.save(ret);
	        }
			return null;
	}

	@Override
	public List<BookReturn> getPendingRequests() {
		 return returnBookRepo.findByStatus("PENDING");
	}

	@Override
	@Transactional
	public void approveRequest(int requestId) {
		 BookReturn request = returnBookRepo.findById(requestId).orElseThrow();
	        request.setStatus("APPROVED");
	        returnBookRepo.save(request);
		
	}

	@Override
	public void rejectRequest(int requestId) {
		  BookReturn request = returnBookRepo.findById(requestId).orElseThrow();
	        request.setStatus("REJECTED");
	        returnBookRepo.save(request);
		
	}

	

}
