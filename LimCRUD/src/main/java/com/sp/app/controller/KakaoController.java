package com.sp.app.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sp.app.domain.Member;
import com.sp.app.service.MemberService;

@Controller("kakao.kakaoController")
@RequestMapping("/kakao/*")
public class KakaoController {
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "code")
	public String getCode(
			@RequestParam(value = "code", required = false) String code, 
			final RedirectAttributes reAttr,
			Model model) throws Exception {
		// code는 인가된 코드
		// access_Token은 접속시 필요한 토큰정보들
		String access_Token = memberService.getAccessToken(code);
		//System.out.println("###access_Token#### : " + access_Token);
        
		// kakaoInfo에는 Map형태로 접속한 카카오 아이디의 이름과 이메일이 저장되어있음
		HashMap<String, Object> kakaoInfo = memberService.getKakaoInfo(access_Token);
		//System.out.println("토큰정보 : "+ access_Token);
		//System.out.println("이름 : "+ kakaoInfo.get("user_name"));
		//System.out.println("아이디 : "+ kakaoInfo.get("user_id"));
		
		// 카카오 아이디로 회원가입되어있다면 -> 메인페이지로 이동
		Member dto = memberService.readMember((String)kakaoInfo.get("user_id"));
		if(dto != null) {
			reAttr.addFlashAttribute("message", "이미 가입된 회원입니다.");
			
			return "redirect:/board/main";
		}

		// 카카오 아이디로 회원가입 안되어있으면 -> 회원가입 창으로 이동
		else {
			dto = new Member();
			dto.setUserId((String)kakaoInfo.get("user_id"));
			dto.setUserName((String)kakaoInfo.get("user_name"));
			
			model.addAttribute("mode", "other");
			model.addAttribute("dto", dto);
			
			return "member/member";
		}
		
	}
	
}
