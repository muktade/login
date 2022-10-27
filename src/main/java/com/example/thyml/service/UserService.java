package com.example.thyml.service;

import java.util.List;

import com.example.thyml.dto.UserDto;
import com.example.thyml.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	User saveUser(User user);
	
	List<User> getUsers();
	
	void deleteUser(Long id);
	
	User getUser(Long id);
}
