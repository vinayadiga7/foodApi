package com.FoodCourt.Login;

import java.util.Date;

public class UserLogin {
	
		private String userName;
		private String password;
		private Date loginDateTime;
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Date getLoginDateTime() {
			return loginDateTime;
		}
		public void setLoginDateTime(Date loginDateTime) {
			this.loginDateTime = loginDateTime;
		}
		
}
