package com.book.app.service;

import java.util.List;


import org.springframework.data.domain.Page;

import com.book.app.model.Category;

public interface CategoryService {

	public Category saveCategory(Category category);
	
	public Boolean existCategory(String name);

	public List<Category> getAllCategory();

	public Boolean deleteCategory(int id);

	public Category getCategoryById(int id);

	public List<Category> getAllActiveCategory();

	public Page<Category> getAllCategorPagination(Integer pageNo,Integer pageSize);
}
