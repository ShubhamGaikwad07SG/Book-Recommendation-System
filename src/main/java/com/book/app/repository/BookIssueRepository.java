package com.book.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.app.model.BookIssue;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Integer> {

	 List<BookIssue> findByUserId(int userId); 
}
