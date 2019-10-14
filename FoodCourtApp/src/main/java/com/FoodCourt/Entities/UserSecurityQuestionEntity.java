package com.FoodCourt.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserSecurityQuestionEntity {

	@Id
	private Integer userSecurityQuestionId;
	
	@Column(name="question",length=50)
	private String securityQuestion;
	public Integer getUserSecurityQuestionId() {
		return userSecurityQuestionId;
	}
	public void setUserSecurityQuestionId(Integer userSecurityQuestionId) {
		this.userSecurityQuestionId = userSecurityQuestionId;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	
	
}
