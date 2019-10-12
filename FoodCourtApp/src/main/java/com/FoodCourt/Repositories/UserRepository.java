package com.FoodCourt.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodCourt.Entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{
		
	public User findByUserName(String username);
}
