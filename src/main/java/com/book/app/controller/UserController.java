package com.book.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.book.app.model.Book;
import com.book.app.model.BookReview;
import com.book.app.model.Category;
import com.book.app.model.User;
import com.book.app.service.BookRequestService;
import com.book.app.service.BookReturnService;
import com.book.app.service.BookReviewService;
import com.book.app.service.BookService;
import com.book.app.service.CategoryService;
import com.book.app.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {	
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookReturnService bookReturnService;
	
	 @Autowired
	 private BookReviewService reviewService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CategoryService categoryService;

	
	@Autowired
	BookRequestService bookrequestService;
	
	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	
	@GetMapping("/profile")
	public String profile() {
		return "user/profile";
	}
	
	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			System.out.println("Shubhamm "+email);
			User user = userService.getUserByEmail(email);
			m.addAttribute("user", user);
			
     		
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	
	
	private User getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		User user = userService.getUserByEmail(email);
		return user;
	}
	
	
	/* Book Request */

	  @GetMapping("/request")
	    public String viewMyRequests(Model model,Principal p) {
	        int userId = userService.getLoggedInUserId(p);
	        model.addAttribute("requests", bookrequestService.getRequestsByUser(userId));
	        System.out.println("User name is  a aaaa"+ bookrequestService.getRequestsByUser(userId));
	        return "user/request_show";
	    }

	    @GetMapping("/requestbook")
	    public String requestBook(@RequestParam("bid") int bookId,@RequestParam("uid") int userId,Principal p) {
	       
	    	bookrequestService.requestBook(userId, bookId);
	        return "redirect:/books"; 
	    }
	    
	    
	    
		/* Return and issue book */
	    
	    @GetMapping("/return-book")
	    public String viewMyReturnBook(Model model,Principal p) {
	        int userId = userService.getLoggedInUserId(p);
	        model.addAttribute("bookreturn", bookrequestService.getRequestsByUser(userId));
	        return "user/return_request";
	    }

	    @GetMapping("/returnbook")
	    public String returnBook(@RequestParam("bid") int bookId,@RequestParam("uid") int userId,Principal p) {
	       
	    	bookReturnService.sendReturnRequest(userId, bookId);
	        return "redirect:/books"; 
	    }
	
	    
	    
		/* ------------------------- Review Book=-------------------- */
	    
	    @GetMapping("/review/{bookId}")
	    public String reviewForm(@PathVariable int bookId, Model model) {
	        model.addAttribute("bookId", bookId);
	        model.addAttribute("review", new BookReview());
	        return "user/review";
	    }

	    @PostMapping("/review-book/{bookId}")
	    public String submitReview(@PathVariable int bookId,
	                               @ModelAttribute BookReview review) {
	    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	 
	    	 if (authentication == null || !authentication.isAuthenticated()) {
	    	        return "redirect:/login";
	    	    }

	    	 String email = authentication.getName();
	    	   User user = userService.getUserByEmail(email);
	        Book book = bookService.getBookById(bookId);
	       
	        review.setBook(book);
	        review.setUser(user);
	        reviewService.saveReview(review);
	        return "redirect:/books";
	    }
	    
}
