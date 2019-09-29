package com.FoodCourt.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(method=RequestMethod.GET, value="/sayGreetings")
	public String sayGreetings()
	{
		return "Hey Vinay!! Looking crazy";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/user/{username}/{password}")
	public User userLogin(@PathVariable("username") String username,@PathVariable("password") String password)
	{
		return loginService.userLogin(username,password);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/users")
	public void createUser(@RequestBody User user)
	{
		loginService.createUser(user);
	}
	
}
