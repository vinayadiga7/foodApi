package com.FoodCourt.Controllers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FoodCourt.Beans.UserAddressBean;
import com.FoodCourt.Beans.UserBean;
import com.FoodCourt.Entities.User;
import com.FoodCourt.Exceptions.InvalidPasswordException;
import com.FoodCourt.Exceptions.InvalidUserNameException;
import com.FoodCourt.Exceptions.PasswordFormatException;
import com.FoodCourt.Services.LoginService;

@RestController
public class LoginController implements LoginControllerInterface {
	
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
		String message = null;
		try {
			message = loginService.createUser(user);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			message = ex.getMessage();
		}
		return message;
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
	
	@RequestMapping(method=RequestMethod.PUT,value="/foodCourtApi/users/{userName}/{newPassword}")
	public String updatePassword(@PathVariable("userName") String userName, @PathVariable("newPassword") String newPassword) {
		
		StringBuilder message = new StringBuilder();
		try {
			message.append(loginService.updateUserPassword(userName,newPassword));
		} catch (NoSuchAlgorithmException e) {
			
			message.append(e.getMessage());	
		} catch (NoSuchProviderException e) {
			
			message.append(e.getMessage());
		} catch (PasswordFormatException e) {
			message.append(e.getMessage());
		}
		return message.toString();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/foodCourtApi/user/{userId}/userAddress")
	public UserAddressBean createUserAddress(@RequestBody UserAddressBean userAddressBean, @PathVariable Integer userId) {
		
		return loginService.createUserAddress(userAddressBean, userId);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/foodCourtApi/user/{userId}/userAddresses")
	public List<UserAddressBean> getUserAddress(@PathVariable Integer userId){
		List<UserAddressBean> userAddressBeanList = new ArrayList<>();
		userAddressBeanList = loginService.getUserAddress(userId);	
		return userAddressBeanList;
	}
	
	
	@RequestMapping(method=RequestMethod.PUT, value="/foodCourtApi/user/{userId}/userAddress")
	public UserAddressBean updateUserAddress(@RequestBody UserAddressBean userAddressBean)
	{
		return loginService.updateUserAddress(userAddressBean);
	}
}
