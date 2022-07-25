package com.sp.app.domain;

// 세션에 저장할 정보들(아이디, 이름, 권한)
public class SessionInfo {
	private String UserId;
	private String UserName;
	private String UserEmail;
	private int UserRole;
	
	
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
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
	public int getUserRole() {
		return UserRole;
	}
	public void setUserRole(int userRole) {
		UserRole = userRole;
	}
	
}
