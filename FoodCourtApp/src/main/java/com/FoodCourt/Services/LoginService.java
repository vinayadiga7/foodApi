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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodCourt.Beans.UserAddressBean;
import com.FoodCourt.Beans.UserBean;
import com.FoodCourt.Entities.User;
import com.FoodCourt.Entities.UserAddressEntity;
import com.FoodCourt.Exceptions.InvalidPasswordException;
import com.FoodCourt.Exceptions.InvalidUserNameException;
import com.FoodCourt.Exceptions.PasswordFormatException;
import com.FoodCourt.Exceptions.UniqueEmailAddressException;
import com.FoodCourt.Exceptions.UniqueUserNameException;
import com.FoodCourt.Repositories.UserAddressEntityRepository;
import com.FoodCourt.Repositories.UserRepository;

@Service
public class LoginService implements LoginServiceInterface {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserAddressEntityRepository userAddressEntityRepository;
	
	
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
	
	
	public String createUser(UserBean userBean) throws UniqueUserNameException, PasswordFormatException, UniqueEmailAddressException {
		
		User userEntity =  new User();
		Boolean passwordCheck;
		
		userEntity.setBirthDate(userBean.getBirthDate());
		userEntity.setEmailAddress(userBean.getEmailAddress());
		userEntity.setFirstName(userBean.getFirstName());
		userEntity.setIsActive(true);
		userEntity.setLastName(userBean.getLastName());
		userEntity.setMobileNumber(userBean.getMobileNumber());
		userEntity.setUserName(userBean.getUserName());
		

		passwordCheck =	checkPasswordForamt(userBean.getPassword());
		if(passwordCheck==false)
		{
			throw new PasswordFormatException("Password should be atleast 8 characters in length, should contain atleast one digit, one lowercase character, one uppercase character,one special character like !,@,#,$,%.");
		}
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
		try {
			userRepository.save(userEntity);
		}
		catch(Exception ex)
		{
			//Unique username constraint
			if(ex.getMessage().contains("UK_lqjrcobrh9jc8wpcar64q1bfh"))
				throw new UniqueUserNameException("There already exists a user with this username. Please choose a different username");
			else if(ex.getMessage().contains("UK_d0ar1h7wcp7ldy6qg5859sol6"))
				throw new UniqueEmailAddressException("Email Address should be unique.");
	
		}
		System.out.println("User got inserted successfully with userId: ");
		
		return "success";
	}


	public static StringBuffer createHashedPassword(String password,byte[] salt) {
		StringBuffer stringBuffer = new StringBuffer();
		String staticSalt = "&3Dfr56[PV";
		try {
//			saltAndHash.add(salt.toString());
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(staticSalt.getBytes());
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
	
	private static Boolean checkPasswordForamt(String password) {
		Boolean passwordCheck;
		Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!])).{8,20}");
		Matcher matcher = pattern.matcher(password);
		passwordCheck = matcher.matches();
		return passwordCheck; 
	}


	public String updateUserPassword(String userName, String newPassword) throws PasswordFormatException, NoSuchAlgorithmException, NoSuchProviderException {
		User userEntity = userRepository.findByUserName(userName);
		Boolean passwordCheck = LoginService.checkPasswordForamt(newPassword);
		if(passwordCheck == false)
			throw new PasswordFormatException("Password should be atleast 8 characters in length, should contain atleast one digit, one lowercase character, one uppercase character,one special character like !,@,#,$,%.");
		
		byte[] salt = getSaltInBytes();
		StringBuffer hashCodedPassword = createHashedPassword(newPassword,salt);
		userEntity.setSalt(salt);
		userEntity.setPassword(hashCodedPassword.toString());
		if( userRepository.save(userEntity)== null)
			return "There was error while updating the password";
		else
			return "You have updated the password successfully";
	}


	public UserAddressBean createUserAddress(UserAddressBean userAddressBean, Integer userId) {
		
		User userEntity = userRepository.getOne(userId);
		
		UserAddressEntity userAddressEntity = new UserAddressEntity();
		userAddressEntity.setCity(userAddressBean.getCity());
		userAddressEntity.setCountry(userAddressBean.getCountry());
		userAddressEntity.setFlatNumber(userAddressBean.getFlatNumber());
		userAddressEntity.setIsActive(userAddressBean.getIsActive());
		userAddressEntity.setLocality(userAddressBean.getLocality());
		userAddressEntity.setPhone(userAddressBean.getPhone());
		userAddressEntity.setPinCode(userAddressBean.getPinCode());
		userAddressEntity.setState(userAddressBean.getState());
		userAddressEntity.setStreetName(userAddressBean.getStreetName());
		userAddressEntity.setUser(userEntity);
		
		UserAddressEntity userAddressEntityModified = userAddressEntityRepository.save(userAddressEntity);
		
		UserAddressBean userAddressBeanModified = new UserAddressBean();
		userAddressBeanModified.setCity(userAddressEntityModified.getCity());
		userAddressBeanModified.setCountry(userAddressEntityModified.getCountry());
		userAddressBeanModified.setFlatNumber(userAddressEntityModified.getFlatNumber());
		userAddressBeanModified.setIsActive(userAddressEntityModified.getIsActive());
		userAddressBeanModified.setLocality(userAddressEntityModified.getLocality());
		userAddressBeanModified.setPhone(userAddressEntityModified.getPhone());
		userAddressBeanModified.setPinCode(userAddressEntityModified.getPinCode());
		userAddressBeanModified.setState(userAddressEntityModified.getState());
		userAddressBeanModified.setStreetName(userAddressEntityModified.getStreetName());
		userAddressBeanModified.setUserAddressId(userAddressEntityModified.getUserAddressId());
		userAddressBeanModified.setUserId(userAddressEntityModified.getUser().getUserId());
		
		return userAddressBeanModified;
	}


	public List<UserAddressBean> getUserAddress(Integer userId) {
		List<UserAddressBean> userAddressBeanList = new ArrayList<>();
		List<UserAddressEntity> userAddressEntityAsList = new ArrayList<>();
		List<UserAddressEntity> userAddressEntityList  = userAddressEntityRepository.findByUserUserId(userId);
		//userAddressEntityList.ifPresent(userAddressEntityAsList::add);
		
		for(UserAddressEntity userAddressEntity : userAddressEntityList) {
			UserAddressBean userAddressBean = new UserAddressBean();
			userAddressBean.setCity(userAddressEntity.getCity());
			userAddressBean.setCountry(userAddressEntity.getCountry());
			userAddressBean.setFlatNumber(userAddressEntity.getFlatNumber());
			userAddressBean.setIsActive(userAddressEntity.getIsActive());
			userAddressBean.setLocality(userAddressEntity.getLocality());
			userAddressBean.setPhone(userAddressEntity.getPhone());
			userAddressBean.setPinCode(userAddressEntity.getPinCode());
			userAddressBean.setState(userAddressEntity.getState());
			userAddressBean.setStreetName(userAddressEntity.getStreetName());
			userAddressBean.setUserAddressId(userAddressEntity.getUserAddressId());
			userAddressBean.setUserId(userAddressEntity.getUser().getUserId());
			
			userAddressBeanList.add(userAddressBean);
		}
		
		return userAddressBeanList;
	}


	public UserAddressBean updateUserAddress(UserAddressBean userAddressBean) {
		UserAddressEntity userAddressEntity = userAddressEntityRepository.getOne(userAddressBean.getUserAddressId());
		
		userAddressEntity.setCity(userAddressBean.getCity());
		userAddressEntity.setCountry(userAddressBean.getCountry());
		userAddressEntity.setFlatNumber(userAddressBean.getFlatNumber());
		userAddressEntity.setIsActive(userAddressBean.getIsActive());
		userAddressEntity.setLocality(userAddressBean.getLocality());
		userAddressEntity.setPhone(userAddressBean.getPhone());
		userAddressEntity.setPinCode(userAddressBean.getPinCode());
		userAddressEntity.setState(userAddressBean.getState());
		userAddressEntity.setStreetName(userAddressBean.getStreetName());
		//userAddressEntity.setUser(userEntity);
		
		UserAddressEntity userAddressEntityModified =  userAddressEntityRepository.save(userAddressEntity);
		UserAddressBean userAddressBeanModified = new UserAddressBean();
		
		userAddressBeanModified.setCity(userAddressEntityModified.getCity());
		userAddressBeanModified.setCountry(userAddressEntityModified.getCountry());
		userAddressBeanModified.setFlatNumber(userAddressEntityModified.getFlatNumber());
		userAddressBeanModified.setIsActive(userAddressEntityModified.getIsActive());
		userAddressBeanModified.setLocality(userAddressEntityModified.getLocality());
		userAddressBeanModified.setPhone(userAddressEntityModified.getPhone());
		userAddressBeanModified.setPinCode(userAddressEntityModified.getPinCode());
		userAddressBeanModified.setState(userAddressEntityModified.getState());
		userAddressBeanModified.setStreetName(userAddressEntityModified.getStreetName());
		userAddressBeanModified.setUserAddressId(userAddressEntityModified.getUserAddressId());
		userAddressBeanModified.setUserId(userAddressEntityModified.getUser().getUserId());
		
		return userAddressBeanModified;
	}
	
	
	

}
