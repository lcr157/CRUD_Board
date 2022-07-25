package com.sp.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="board")
public class Board implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "num", columnDefinition = "NUMBER")
	@SequenceGenerator(name = "board_seq", sequenceName = "board_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
	private long num; 			// pk이자 시퀀스로 만듬
	
	@Column(name= "UserId", nullable = false)
	private String UserId;
	
	@Column(name= "UserName")
	private String UserName;
	
	@Column(name= "Subject", nullable = false, length = 500)
	private String Subject;
	
	@Column(name= "Content", nullable = false, length = 4000)
	private String Content;
	
	@Column(nullable = false, columnDefinition = "DATE DEFAULT SYSDATE", updatable = false)
	private String RegDate;
	
	@Column(name="HitCount", nullable = false, columnDefinition = "NUMBER DEFAULT 0",
			insertable = false, updatable = false)
	private int HitCount;
	
	
	// insert전에 호출되는 어노테이션 -> 
	@PrePersist
	public void prePersist() {
		this.RegDate = this.RegDate == null ?
				new java.sql.Date(new java.util.Date().getTime()).toString() : this.RegDate;
	}


	public long getNum() {
		return num;
	}


	public void setNum(long num) {
		this.num = num;
	}


	public String getUserId() {
		return UserId;
	}


	public void setUserId(String userId) {
		UserId = userId;
	}


	public String getSubject() {
		return Subject;
	}


	public void setSubject(String subject) {
		Subject = subject;
	}


	public String getContent() {
		return Content;
	}


	public void setContent(String content) {
		Content = content;
	}


	public String getRegDate() {
		return RegDate;
	}


	public void setRegDate(String regDate) {
		RegDate = regDate;
	}


	public int getHitCount() {
		return HitCount;
	}


	public void setHitCount(int hitCount) {
		HitCount = hitCount;
	}


	public String getUserName() {
		return UserName;
	}


	public void setUserName(String userName) {
		UserName = userName;
	}

}
