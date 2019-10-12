package com.FoodCourt.Beans;

import java.time.LocalDate;
import java.util.Date;

public class UserBean {
	
		private Integer userId;
		private String userName;
		private String password;
		private String firstName;
		private String lastName;
		private String mobileNumber;
		private String emailAddress;
		private LocalDate birthDate;
		private Boolean isActive;
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
	
		
		
		
		
		
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getMobileNumber() {
			return mobileNumber;
		}
		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		public LocalDate getBirthDate() {
			return birthDate;
		}
		public void setBirthDate(LocalDate birthDate) {
			this.birthDate = birthDate;
		}
		public Boolean getIsActive() {
			return isActive;
		}
		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
	
		
}
