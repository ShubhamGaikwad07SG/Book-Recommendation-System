package com.book.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.app.model.Auther;

public interface AutherRepository extends JpaRepository<Auther,Integer> {

	public Boolean existsByName(String name);
}
