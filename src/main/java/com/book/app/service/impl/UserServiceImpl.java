package com.book.app.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.book.app.model.User;
import com.book.app.repository.UserRepository;
import com.book.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {
		user.setRole("ROLE_USER");
		user.setIsEnable(true);
		user.setAccountNonLocked(true);
		user.setFailedAttempt(0);

		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		User saveUser = userRepository.save(user);
		return saveUser;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getUsers(String role) {
		return userRepository.findByRole(role);
	}

	@Override
	public Boolean updateAccountStatus(Integer id, Boolean status) {
		Optional<User> findByuser = userRepository.findById(id);

		if (findByuser.isPresent()) {
			User user = findByuser.get();
			user.setIsEnable(status);
			userRepository.save(user);
			return true;
		}

		return false;
	}

	@Override
	public void increaseFailedAttempt(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userAccountLock(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean unlockAccountTimeExpired(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetAttempt(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserResetToken(String email, String resetToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUserByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUserProfile(User user, MultipartFile img) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveAdmin(User user) {
		
		user.setRole("ROLE_ADMIN");
		user.setIsEnable(true);
		user.setAccountNonLocked(true);
		user.setFailedAttempt(0);

		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		User saveUser = userRepository.save(user);
		return saveUser;
	}

	@Override
	public Boolean existsEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public int getLoggedInUserId(Principal p) {
		String username=p.getName();
		User user=userRepository.findByEmail(username);
		return user.getId();
	}

	

	
	
}
