package com.book.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.app.model.Book;

@Repository
public interface BookRepository extends JpaRepository <Book ,Integer> {
	
	List<Book> findByIsActiveTrue();

	Page<Book> findByIsActiveTrue(Pageable pageable);

	List<Book> findByCategory(String category);

	List<Book> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2);

	Page<Book> findByCategory(Pageable pageable, String category);

	Page<Book> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2,
			Pageable pageable);

	Page<Book> findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2,
			Pageable pageable);

}
