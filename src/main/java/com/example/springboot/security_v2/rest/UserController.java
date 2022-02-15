package com.example.springboot.security_v2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.security_v2.entity.User;
import com.example.springboot.security_v2.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
		
	@PostMapping("/admin/save")
	@ResponseBody
	public User saveUser(@RequestBody User user) {
		user.setId(0L);
		User savedUser = userRepository.save(user);
		return savedUser;
	}

}
