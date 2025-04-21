package com.book.app.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.book.app.model.Book;
import com.book.app.repository.BookRepository;
import com.book.app.repository.BookRequestRepository;
import com.book.app.service.BookService;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookRequestRepository bookRequestRepository;
	
	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	@Transactional
	public Boolean deleteBook(Integer id) {
		Book book = bookRepository.findById(id).orElse(null);
		bookRequestRepository.deleteByBookId(id);

		if (!ObjectUtils.isEmpty(book)) {
			bookRepository.delete(book);
			return true;
		}
		return false;
	}

	@Override
	public Book getBookById(Integer id) {
		Book book = bookRepository.findById(id).orElse(null);
		return book;
	}

	@Override
	public Book updateBook(Book book, MultipartFile file) {
		Book dbBook = getBookById(book.getId());

		String imageName = file.isEmpty() ? dbBook.getImage() : file.getOriginalFilename();

		dbBook.setTitle(book.getTitle());
		dbBook.setDescription(book.getDescription());
		dbBook.setCategory(book.getCategory());
//		dbBook.setPrice(book.getPrice());
		dbBook.setStock(book.getStock());
		dbBook.setImage(imageName);
		dbBook.setIsActive(book.getIsActive());
//		dbBook.setDiscount(book.getDiscount());


		Book updateProduct = bookRepository.save(dbBook);

		if (!ObjectUtils.isEmpty(updateProduct)) {

			if (!file.isEmpty()) {

				try {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
							+ file.getOriginalFilename());
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return book;
		}
		return null;
	}

	@Override
	public List<Book> getAllActiveBooks(String category) {
		List<Book> books = null;
		if (ObjectUtils.isEmpty(category)) {
			books = bookRepository.findByIsActiveTrue();
		} else {
			books = bookRepository.findByCategory(category);
		}

		return books;
	}

	@Override
	public List<Book> searchBook(String ch) {
		return bookRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
	}

	@Override
	public Page<Book> getAllActiveBookPagination(Integer pageNo, Integer pageSize, String category) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Book> pageBook = null;

		if (ObjectUtils.isEmpty(category)) {
			pageBook = bookRepository.findByIsActiveTrue(pageable);
		} else {
			pageBook = bookRepository.findByCategory(pageable, category);
		}
		return pageBook;
	}

	@Override
	public Page<Book> searchBookPagination(Integer pageNo, Integer pageSize, String ch) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return bookRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch, pageable);
	
	}

	@Override
	public Page<Book> getAllBooksPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return bookRepository.findAll(pageable);
	}

	@Override
	public Page<Book> searchActiveBookPagination(Integer pageNo, Integer pageSize, String category, String ch) {
		Page<Book> pageBook = null;
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		pageBook = bookRepository.findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch,
				ch, pageable);

//		if (ObjectUtils.isEmpty(category)) {
//			pageProduct = productRepository.findByIsActiveTrue(pageable);
//		} else {
//			pageProduct = productRepository.findByCategory(pageable, category);
//		}
		return pageBook;
	}

}
