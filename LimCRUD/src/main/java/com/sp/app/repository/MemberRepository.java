package com.sp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sp.app.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	// JPQL
	
	// 아이디,패스워드 확인하기
	@Query(
			value = "SELECT User_Id, User_Pwd, User_Name, User_Email, Reg_Date, Modified_Date, Role FROM member WHERE User_Id =:UserId and User_Pwd =:UserPwd ",
			nativeQuery = true
	)
	public Member loginMember(@Param("UserId") String UserId, @Param("UserPwd") String UserPwd);
	
}
