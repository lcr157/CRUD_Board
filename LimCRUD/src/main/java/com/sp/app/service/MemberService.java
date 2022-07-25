package com.sp.app.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sp.app.domain.Member;

public interface MemberService {
	// 회원가입
	public void insertMember(Member dto) throws Exception;
	
	// 아이디를 통한 회원정보 확인
	public Member readMember(String id);
	
	// 로그인
	public Member loginMember(Map<String, String> map) throws Exception;
	
	// 정보수정
	public void updateMember(Member dto) throws Exception;
	
	// 회원삭제
	public void deleteMember(String UesrId) throws Exception;
	
	
	
	// 카카오 로그인
	// 인가된 코드를 이용하여 토큰정보 얻는 함수
	public String getAccessToken (String authorize_code) throws IOException;
	
	// 토큰정보로 사용자 정보 요청
	public HashMap<String, Object> getKakaoInfo(String access_Token);
}
