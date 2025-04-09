package com.book.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.book.app.model.Auther;
import com.book.app.model.Book;
import com.book.app.model.Category;
import com.book.app.service.AutherService;
import com.book.app.service.BookRequestService;
import com.book.app.service.BookService;
import com.book.app.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
     @Autowired 
     CategoryService categoryService;
     
     @Autowired
     BookService bookService;
     
     @Autowired
     AutherService autherService;
     
     @Autowired
     BookRequestService bookrequestService;
	
	@GetMapping("/")
	public String index() {
		return "admin/index";
	}
	
// ---------------------	Category Section-----------------------------------------------
	
	@GetMapping("/category")
	public String category(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		// m.addAttribute("categorys", categoryService.getAllCategory());
		Page<Category> page = categoryService.getAllCategorPagination(pageNo, pageSize);
		List<Category> categorys = page.getContent();
		m.addAttribute("categorys", categorys);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/category";
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		category.setImageName(imageName);
		
		Boolean existCategory = categoryService.existCategory(category.getName());

		if (existCategory) {
			session.setAttribute("errorMsg", "Category Name already exists");
		} else {

			Category saveCategory = categoryService.saveCategory(category);

			if (ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not saved ! internal server error");
			} else {

				File saveFile = new ClassPathResource("static/image").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "Category" + File.separator
						+ file.getOriginalFilename());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				session.setAttribute("succMsg", "Saved successfully");
			}
		}

		return "redirect:/admin/category";
	}
	
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategory(id);

		if (deleteCategory) {
			session.setAttribute("succMsg", "category delete success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/category";
	}

	
	// ---------------------  End	Category Section-----------------------------------------------
	

	
	// --------------------- Start	Auther Section-----------------------------------------------
	
	@GetMapping("/auther")
	public String auther(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		// m.addAttribute("categorys", categoryService.getAllCategory());
		Page<Auther> page = autherService.getAllAutherPagination(pageNo, pageSize);
		List<Auther> authers = page.getContent();
		m.addAttribute("authers", authers);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/auther";
	}
	
	@PostMapping("/saveAuther")
	public String saveAuther(@ModelAttribute Auther auther,HttpSession session) throws IOException {

		
		Boolean existAuther = autherService.existAuther(auther.getName());

		if (existAuther) {
			session.setAttribute("errorMsg", "Auther Name already exists");
		} else {

			Auther saveAuther = autherService.saveAuther(auther);

			if (ObjectUtils.isEmpty(saveAuther)) {
				session.setAttribute("errorMsg", "Not saved ! internal server error");
			} else {
				session.setAttribute("succMsg", "Saved successfully");
			}
		}

		return "redirect:/admin/auther";
	}

	// ---------------------  End	Auther Section-----------------------------------------------
	
	@GetMapping("/loadAddBook")
	public String loadAddBook(Model m) {
		List<Category> categories = categoryService.getAllCategory();
		List<Auther> authers = autherService.getAllAuther();
		m.addAttribute("categories", categories);
		m.addAttribute("auther", authers);
		return "admin/add_book";
	}
	
	
	
	
	
	@GetMapping("/books")
	public String loadViewProduct(Model m, @RequestParam(defaultValue = "") String ch,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		Page<Book> page = null;
		if (ch != null && ch.length() > 0) {
			page = bookService.searchBookPagination(pageNo, pageSize, ch);
		} else {
			page = bookService.getAllBooksPagination(pageNo, pageSize);
		}
		m.addAttribute("books", page.getContent());

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/books";
	}

	
	@PostMapping("/saveBook")
	public String saveProduct(@ModelAttribute Book book, @RequestParam("file") MultipartFile image,
			HttpSession session) throws IOException {

		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();

		book.setImage(imageName);
		Book saveBook = bookService.saveBook(book);

		if (!ObjectUtils.isEmpty(saveBook)) {

			File saveFile = new ClassPathResource("static/image").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "books" + File.separator
					+ image.getOriginalFilename());

			// System.out.println(path);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			session.setAttribute("succMsg", "Product Saved Success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/loadAddBook";
}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable int id, HttpSession session) {
		Boolean deleteBook = bookService.deleteBook(id);
		if (deleteBook) {
			session.setAttribute("succMsg", "Product delete success");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/admin/books";
	}

	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model m) {
		m.addAttribute("book", bookService.getBookById(id));
		m.addAttribute("categories", categoryService.getAllCategory());
		return "admin/edit_book";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Book book, @RequestParam("file") MultipartFile image,
			HttpSession session, Model m) {

		Book updateProduct = bookService.updateBook(book, image);
		if (!ObjectUtils.isEmpty(updateProduct)) {
			session.setAttribute("succMsg", "Product update success");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/admin/editProduct/" + book.getId();
	}


/* Book Request */


	
	  @GetMapping("/request")
	    public String viewAllRequests(Model model) {
	        model.addAttribute("requests", bookrequestService.getAllRequests());
	        return "admin/request";
	    }

	    @PostMapping("/approve/{id}")
	    public String approveRequest(@PathVariable int id) {
	        bookrequestService.updateRequestStatus(id, "APPROVED");
	        return "redirect:/admin/request";
	    }

	    @PostMapping("/reject/{id}")
	    public String rejectRequest(@PathVariable int id) {
	    	bookrequestService.updateRequestStatus(id, "REJECTED");
	        return "redirect:/admin/request";
	    }
}
	