package com.FoodCourt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FoodCourt.Beans.UserBean;
import com.FoodCourt.Entities.User;
import com.FoodCourt.Exceptions.InvalidPasswordException;
import com.FoodCourt.Exceptions.InvalidUserNameException;
import com.FoodCourt.Services.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(method=RequestMethod.GET, value="/sayGreetings")
	public String sayGreetings()
	{
		return "Hey Vinay!! Looking crazy";
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/foodCourtApi/users")
	public String registerUser(@RequestBody UserBean user)
	{
		return loginService.createUser(user);
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/foodCourtApi/users/{username}/{password}")
	public UserBean userLogin(@PathVariable("username") String username,@PathVariable("password") String password)
	{
		UserBean userBean = new UserBean();
		try {
			userBean = loginService.userLogin(username,password);
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (InvalidUserNameException e) {
			e.printStackTrace();
		}
		
		return userBean;
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/foodCourtApi/users")
	public UserBean userUpdate(@RequestBody UserBean userBean)
	{
			return loginService.updateUser(userBean);
	}
	
	
	

	
}
