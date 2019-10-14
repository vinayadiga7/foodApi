package com.FoodCourt.Services;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import com.FoodCourt.Beans.UserAddressBean;
import com.FoodCourt.Beans.UserBean;
import com.FoodCourt.Exceptions.InvalidPasswordException;
import com.FoodCourt.Exceptions.InvalidUserNameException;
import com.FoodCourt.Exceptions.PasswordFormatException;
import com.FoodCourt.Exceptions.UniqueEmailAddressException;
import com.FoodCourt.Exceptions.UniqueUserNameException;

public interface LoginServiceInterface {

	public UserBean userLogin(String username, String password) throws InvalidPasswordException, InvalidUserNameException;
	public String createUser(UserBean userBean) throws UniqueUserNameException, PasswordFormatException, UniqueEmailAddressException;
	public UserBean updateUser(UserBean userBean);
	public String updateUserPassword(String userName, String newPassword) throws PasswordFormatException, NoSuchAlgorithmException, NoSuchProviderException;
	public UserAddressBean createUserAddress(UserAddressBean userAddressBean, Integer userId);
	public List<UserAddressBean> getUserAddress(Integer userId);
	public UserAddressBean updateUserAddress(UserAddressBean userAddressBean);
}
