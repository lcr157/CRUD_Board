package com.sp.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="member")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="UserId", nullable = false)
	private String UserId;
	
	@Column(name="UserPwd", nullable = false)
	private String UserPwd;
	
	@Column(name="UserName", nullable = false)
	private String UserName;
	
	@Column(name="UserEmail")
	private String UserEmail;
	
	@Column(nullable = false, columnDefinition = "DATE DEFAULT SYSDATE", updatable = false)
	private String RegDate;
	
	@Column(name="ModifiedDate")
	private String ModifiedDate;
	
	@Column(name="Role", columnDefinition = "NUMBER DEFAULT 0")
	private int Role;
	
	// insert전에 호출되는 어노테이션 -> 
	@PrePersist
	public void prePersist() {
		this.RegDate = this.RegDate == null ?
				new java.sql.Date(new java.util.Date().getTime()).toString() : this.RegDate;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserPwd() {
		return UserPwd;
	}

	public void setUserPwd(String userPwd) {
		UserPwd = userPwd;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public String getRegDate() {
		return RegDate;
	}

	public void setRegDate(String regDate) {
		RegDate = regDate;
	}

	public String getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	public int getRole() {
		return Role;
	}

	public void setRole(int role) {
		Role = role;
	}
	
}
