package com.book.app.service;

import java.util.List;

import com.book.app.model.BookIssue;

public interface BookIssueService {

	 List<BookIssue> getIssuedBooksByUser(int userId);
	    void requestReturn(int issuedBookId);
	    void acceptReturn(int issuedBookId);
		List<BookIssue> getPendingReturns();
}
