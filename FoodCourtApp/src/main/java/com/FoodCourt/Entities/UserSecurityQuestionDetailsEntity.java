package com.FoodCourt.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserSecurityQuestionDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userSecurityQuestionDetailsId;
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	@ManyToOne
	@JoinColumn(name="userSecurityQuestionId")
	private UserSecurityQuestionEntity userSecurityQuestionEntity;
	@Column(length=30)
	private String userSecurityQuestionAnswer;
	public Integer getUserSecurityQuestionDetailsId() {
		return userSecurityQuestionDetailsId;
	}
	public void setUserSecurityQuestionDetailsId(Integer userSecurityQuestionDetailsId) {
		this.userSecurityQuestionDetailsId = userSecurityQuestionDetailsId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserSecurityQuestionEntity getUserSecurityQuestionEntity() {
		return userSecurityQuestionEntity;
	}
	public void setUserSecurityQuestionEntity(UserSecurityQuestionEntity userSecurityQuestionEntity) {
		this.userSecurityQuestionEntity = userSecurityQuestionEntity;
	}
	public String getUserSecurityQuestionAnswer() {
		return userSecurityQuestionAnswer;
	}
	public void setUserSecurityQuestionAnswer(String userSecurityQuestionAnswer) {
		this.userSecurityQuestionAnswer = userSecurityQuestionAnswer;
	}
	
	
	
}
