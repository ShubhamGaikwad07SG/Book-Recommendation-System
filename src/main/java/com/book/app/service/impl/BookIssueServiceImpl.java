package com.book.app.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.app.model.BookIssue;
import com.book.app.repository.BookIssueRepository;
import com.book.app.service.BookIssueService;

@Service
public class BookIssueServiceImpl implements BookIssueService {

	 @Autowired
	    private BookIssueRepository bookIssuedRepo;
	
	@Override
	public List<BookIssue> getIssuedBooksByUser(int userId) {
		 return bookIssuedRepo.findByUserId(userId);
	}

	@Override
	public void requestReturn(int issuedBookId) {
		BookIssue issued = bookIssuedRepo.findById(issuedBookId).orElseThrow();
	        issued.setReturnDate(LocalDate.now());
	        bookIssuedRepo.save(issued);
		
	}

	@Override
	public void acceptReturn(int issuedBookId) {
		 BookIssue issued = bookIssuedRepo.findById(issuedBookId).orElseThrow();

	        issued.setReturned(true);
	        issued.setReturnAccepted(true);

	        LocalDate issueDate = issued.getIssueDate();
	        LocalDate returnDate = issued.getReturnDate();
	        if (returnDate == null) {
	            returnDate = LocalDate.now();
	            issued.setReturnDate(returnDate);
	        }

	        long days = ChronoUnit.DAYS.between(issueDate, returnDate);
	        if (days > 8) {
	            issued.setFineAmount((days - 8) * 10);
	        }

	        bookIssuedRepo.save(issued);
	    }

	@Override
	public List<BookIssue> getPendingReturns() {
		 List<BookIssue> all = bookIssuedRepo.findAll();
	        return all.stream()
	                .filter(b -> !b.isReturned() && b.getReturnDate() != null)
	                .collect(Collectors.toList());
	}
		
	}

