package com.FoodCourt.Login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	
	private UserRepository userRepository;
	
	
	public User userLogin(String username, String password) {
		
		return userRepository.findByUsernameAndPassword(username, password);
	}
	
	
	public void createUser(User user) {
		userRepository.save(user);
	}
	
	

}
