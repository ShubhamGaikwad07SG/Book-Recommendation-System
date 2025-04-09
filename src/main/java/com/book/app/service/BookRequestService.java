package com.book.app.service;

import java.util.List;

import com.book.app.model.BookRequest;


public interface BookRequestService {

	    void requestBook(int userId, int bookId);
	    List<BookRequest> getRequestsByUser(int userId);
	    List<BookRequest> getAllRequests();
	    void updateRequestStatus(int requestId, String status);
	    
	  

}
