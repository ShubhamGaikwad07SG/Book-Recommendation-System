package com.book.app.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BookIssue {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private LocalDate issueDate;
	    private LocalDate returnDate;
	    private boolean returned;
	    private boolean isReturnAccepted;

	    private double fineAmount;
	    private boolean finePaid;

	    @ManyToOne
	    private Book book;

	    @ManyToOne
	    private User user;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public LocalDate getIssueDate() {
			return issueDate;
		}

		public void setIssueDate(LocalDate issueDate) {
			this.issueDate = issueDate;
		}

		public LocalDate getReturnDate() {
			return returnDate;
		}

		public void setReturnDate(LocalDate returnDate) {
			this.returnDate = returnDate;
		}

		public boolean isReturned() {
			return returned;
		}

		public void setReturned(boolean returned) {
			this.returned = returned;
		}

		public double getFineAmount() {
			return fineAmount;
		}

		public void setFineAmount(double fineAmount) {
			this.fineAmount = fineAmount;
		}

		public boolean isFinePaid() {
			return finePaid;
		}

		public void setFinePaid(boolean finePaid) {
			this.finePaid = finePaid;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public boolean isReturnAccepted() {
			return isReturnAccepted;
		}

		public void setReturnAccepted(boolean isReturnAccepted) {
			this.isReturnAccepted = isReturnAccepted;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	    
	    
}
