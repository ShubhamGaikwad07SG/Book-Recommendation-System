package com.book.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book.app.model.Auther;
import com.book.app.model.Category;
import com.book.app.repository.AutherRepository;
import com.book.app.service.AutherService;

@Service
public class AutherServiceImpl implements AutherService {

	@Autowired
	AutherRepository autherRepository;
	
	@Override
	public Auther saveAuther(Auther auther) {
		
		return autherRepository.save(auther);
	}

	@Override
	public Boolean existAuther(String name) {
		// TODO Auto-generated method stub
		return autherRepository.existsByName(name);
	}

	@Override
	public List<Auther> getAllAuther() {
		
		return autherRepository.findAll();
	}

	@Override
	public Boolean deleteAuther(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auther getAutherById(int id) {
		Auther auther = autherRepository.findById(id).orElse(null);
		return auther;
	}

	@Override
	public List<Auther> getAllActiveAuther() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Auther> getAllAutherPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return autherRepository.findAll(pageable);
	}

}
