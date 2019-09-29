package com.FoodCourt.Login;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface UserRepository  extends CrudRepository<User, Integer>{
		
	public User findByUsernameAndPassword(String username, String password);
}
