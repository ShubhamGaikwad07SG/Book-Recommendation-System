package com.book.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.app.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {


	public User findByEmail(String email);

	public List<User> findByRole(String role);

	public User findByResetToken(String token);

	public Boolean existsByEmail(String email);
	
}
