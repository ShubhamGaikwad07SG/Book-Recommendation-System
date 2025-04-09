package com.book.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.book.app.model.Book;

public interface BookService {

	public Book saveBook(Book book);

	public List<Book> getAllBooks();

	public Boolean deleteBook(Integer id);

	public Book getBookById(Integer id);

	public Book updateBook(Book book, MultipartFile file);

	public List<Book> getAllActiveBooks(String category);

	public List<Book> searchBook(String ch);

	public Page<Book> getAllActiveBookPagination(Integer pageNo, Integer pageSize, String category);

	public Page<Book> searchBookPagination(Integer pageNo, Integer pageSize, String ch);

	public Page<Book> getAllBooksPagination(Integer pageNo, Integer pageSize);

	public Page<Book> searchActiveBookPagination(Integer pageNo, Integer pageSize, String category, String ch);
}
