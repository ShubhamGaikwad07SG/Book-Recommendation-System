package com.book.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.book.app.model.Category;
import com.book.app.model.User;
import com.book.app.service.BookRequestService;

import com.book.app.service.CategoryService;
import com.book.app.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {	
	
	@Autowired
	UserService userService;
	
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
	    
		
}
