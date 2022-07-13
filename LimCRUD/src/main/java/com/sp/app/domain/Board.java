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
@Table(name= "bbs")
public class Board implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "BoardNum", columnDefinition = "NUMBER")
	@SequenceGenerator(name = "bbs_num_generator", sequenceName = "bbs_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bbs_num_generator")
	private int BoardNum; 			// pk이자 시퀀스로 만듬
	
	@Column(name= "UserId", nullable = false)
	private String UserId;
	
	@Column(name= "subject", nullable = false, length = 500)
	private String subject;
	
	@Column(name= "content", nullable = false, length = 4000)
	private String content;
	
	@Column(nullable = false, columnDefinition = "DATE DEFAULT SYSDATE", updatable = false)
	private String reg_date;
	
	@Column(name="hitCount", nullable = false, columnDefinition = "NUMBER DEFATUL 0",
			insertable = false, updatable = false)
	private int hitCount;
	
	
	// insert전에 호출되는 어노테이션 -> 
	@PrePersist
	public void prePersist() {
		this.reg_date = this.reg_date == null ?
				new java.sql.Date(new java.util.Date().getTime()).toString() : this.reg_date;
	}


	public int getBoardNum() {
		return BoardNum;
	}


	public void setBoardNum(int boardNum) {
		BoardNum = boardNum;
	}


	public String getUserId() {
		return UserId;
	}


	public void setUserId(String userId) {
		UserId = userId;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getReg_date() {
		return reg_date;
	}


	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}


	public int getHitCount() {
		return hitCount;
	}


	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

}
