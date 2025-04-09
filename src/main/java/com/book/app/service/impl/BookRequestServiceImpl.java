package com.book.app.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.app.model.Book;
import com.book.app.model.BookRequest;
import com.book.app.model.User;
import com.book.app.repository.BookRepository;
import com.book.app.repository.BookRequestRepository;
import com.book.app.repository.UserRepository;
import com.book.app.service.BookRequestService;

@Service
public class BookRequestServiceImpl implements BookRequestService{

	 @Autowired
	    private BookRequestRepository bookrequestRepo;

	    @Autowired
	    private BookRepository bookRepo;
	    
	    @Autowired
	    private UserRepository userRepo;

		@Override
		public void requestBook(int userId, int bookId) {
			 Optional<User> userOpt = userRepo.findById(userId);
		        Optional<Book> bookOpt = bookRepo.findById(bookId);

		        if (userOpt.isPresent() && bookOpt.isPresent()) {
		            BookRequest req = new BookRequest();
		            req.setUser(userOpt.get());
		            req.setBook(bookOpt.get());
		            req.setRequestDate(LocalDate.now());
		            req.setStatus("PENDING");
		            bookrequestRepo.save(req);
		        }
			
		}
		
		@Override
		public List<BookRequest> getAllRequests() {
			
			return bookrequestRepo.findAll();
		}

		@Override
		public void updateRequestStatus(int requestId, String status) {
			Optional<BookRequest> reqOpt = bookrequestRepo.findById(requestId);
			System.out.println("TTTTTTTTTTTTTTT........."+reqOpt);
	        if (reqOpt.isPresent()) {
	        	BookRequest req = reqOpt.get();
	            req.setStatus(status);
	            req.setIssueDate(LocalDate.now());
	            bookrequestRepo.save(req);
	        }
			
		}

		@Override
		public List<BookRequest> getRequestsByUser(int userId) {
			// TODO Auto-generated method stub
			return bookrequestRepo.findByUserId(userId);
		}
		
	

		



	
}
