package com.FoodCourt.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodCourt.Beans.UserBean;
import com.FoodCourt.Entities.User;
import com.FoodCourt.Exceptions.InvalidPasswordException;
import com.FoodCourt.Exceptions.InvalidUserNameException;
import com.FoodCourt.Repositories.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public UserBean userLogin(String username, String password) throws InvalidPasswordException, InvalidUserNameException {
		
		UserBean userBean = new UserBean();
		User userEntity = userRepository.findByUserName(username);
		if(userEntity==null)
		{
			System.out.println("Invalid username.. there is no user with this username");
			throw new InvalidUserNameException("Username is invalid");
		}
		else {
			
			String hashCodeToBeChecked = createHashedPassword(password,userEntity.getSalt()).toString();
			System.out.println("Salt stored: "+userEntity.getSalt());
			System.out.println("Hashcodeto be checked: "+hashCodeToBeChecked);
			if(hashCodeToBeChecked.equals(userEntity.getPassword()))
			{
				userBean.setBirthDate(userEntity.getBirthDate());
				userBean.setEmailAddress(userEntity.getEmailAddress());
				userBean.setFirstName(userEntity.getFirstName());
				userBean.setIsActive(userEntity.getIsActive());
				userBean.setLastName(userEntity.getLastName());
				userBean.setMobileNumber(userEntity.getMobileNumber());
				userBean.setUserId(userEntity.getUserId());
				userBean.setUserName(userEntity.getUserName());
				userBean.setPassword(userEntity.getPassword());
			}
			else
			{
				throw new InvalidPasswordException("Password is invalid");
			}	
		
		}
			return userBean;
	}
	
	
	public String createUser(UserBean userBean) {
		
		User userEntity =  new User();
		userEntity.setBirthDate(userBean.getBirthDate());
		userEntity.setEmailAddress(userBean.getEmailAddress());
		userEntity.setFirstName(userBean.getFirstName());
		userEntity.setIsActive(true);
		userEntity.setLastName(userBean.getLastName());
		userEntity.setMobileNumber(userBean.getMobileNumber());
		userEntity.setUserName(userBean.getUserName());
		
		byte[] salt = new byte[16];
		StringBuffer hashedPassword = new StringBuffer();
		try {
			salt = getSaltInBytes();
			hashedPassword = createHashedPassword(userBean.getPassword(),salt);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userEntity.setSalt(salt);
		userEntity.setPassword(hashedPassword.toString());
		userRepository.save(userEntity);
		System.out.println("User got inserted successfully with userId: ");
		
		return "success";
	}


	public static StringBuffer createHashedPassword(String password,byte[] salt) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
//			saltAndHash.add(salt.toString());
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(salt);
			byte[] byteArrayInDecimal = messageDigest.digest(password.getBytes());
			
			for(byte byteInHex : byteArrayInDecimal) {
				stringBuffer.append(Integer.toHexString(byteInHex & 0xff).toString());
				//System.out.println(" Hashed passowrd in for each loop "+stringBuffer.toString());
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		
		//saltAndHash.add(stringBuffer.toString());
		return stringBuffer;
	}


	private static byte[] getSaltInBytes() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
		byte[] salt = new byte[16];
		//get a random salt
		secureRandom.nextBytes(salt);
		System.out.println("The salt is "+salt.toString());
		return salt;
	}


	public UserBean updateUser(UserBean userBean) {
		
		User userEntity = userRepository.getOne(userBean.getUserId());
		//userEntity.setUserId(userBean.getUserId());
		userEntity.setUserName(userBean.getUserName());
		userEntity.setBirthDate(userBean.getBirthDate());
		userEntity.setEmailAddress(userBean.getEmailAddress());
		userEntity.setFirstName(userBean.getFirstName());
		//userEntity.setIsActive(userBean.getIsActive());
		userEntity.setLastName(userBean.getLastName());
		userEntity.setMobileNumber(userBean.getMobileNumber());
		//userEntity.setPassword(userBean.getPassword());
		
		User userEntityModified = userRepository.save(userEntity);
		
		UserBean userBeanModified = new UserBean();
		userBeanModified.setBirthDate(userEntityModified.getBirthDate());
		userBeanModified.setEmailAddress(userEntityModified.getEmailAddress());
		userBeanModified.setFirstName(userEntityModified.getFirstName());
		userBeanModified.setIsActive(userEntityModified.getIsActive());
		userBeanModified.setLastName(userEntityModified.getLastName());
		userBeanModified.setMobileNumber(userEntityModified.getMobileNumber());
		userBeanModified.setUserId(userEntityModified.getUserId());
		userBeanModified.setUserName(userEntityModified.getUserName());
		userBeanModified.setPassword(userEntityModified.getPassword());
		
		return userBeanModified;
	}
	
	

}
