package com.book.app.service;
import java.util.List;

import org.springframework.data.domain.Page;

import com.book.app.model.Auther;



public interface AutherService {

	public Auther saveAuther(Auther auther);
	
	public Boolean existAuther(String name);

	public List<Auther> getAllAuther();

	public Boolean deleteAuther(int id);

	public Auther getAutherById(int id);

	public List<Auther> getAllActiveAuther();

	public Page<Auther> getAllAutherPagination(Integer pageNo,Integer pageSize);

}
