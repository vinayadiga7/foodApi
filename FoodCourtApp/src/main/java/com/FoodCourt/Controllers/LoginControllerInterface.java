package com.FoodCourt.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.FoodCourt.Beans.UserAddressBean;
import com.FoodCourt.Beans.UserBean;

public interface LoginControllerInterface {

	public String registerUser(@RequestBody UserBean user);
	public UserBean userLogin(@PathVariable("username") String username,@PathVariable("password") String password);
	public UserBean userUpdate(@RequestBody UserBean userBean);
	public String updatePassword(@PathVariable("userName") String userName, @PathVariable("newPassword") String newPassword);
	public UserAddressBean createUserAddress(@RequestBody UserAddressBean userAddressBean, @PathVariable Integer userId);
	public List<UserAddressBean> getUserAddress(@PathVariable Integer userId);
	public UserAddressBean updateUserAddress(@RequestBody UserAddressBean userAddressBean);
}
