package com.book.app.service;

import java.util.List;

import com.book.app.model.Book;
import com.book.app.model.BookReturn;
import com.book.app.model.User;

public interface BookReturnService {
	
	 BookReturn sendReturnRequest(int userId, int bookId);
	    List<BookReturn> getPendingRequests();
	    void approveRequest(int requestId);
	    void rejectRequest(int requestId);
	   

}
