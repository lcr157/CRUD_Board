package com.sp.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sp.app.domain.Member;
import com.sp.app.domain.SessionInfo;
import com.sp.app.service.MemberService;

@Controller("member.memberController")
@RequestMapping("/member/*")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	// 로그인 get
	@RequestMapping(value = "login", method=RequestMethod.GET)
	public String loginForm() throws Exception {
		
		return "member/login";
	}

	// 로그인 post
	@RequestMapping(value = "login", method=RequestMethod.POST)
	public String loginSubmit(
			Member dto, 
			HttpSession session, 
			final RedirectAttributes reAttr
			) throws Exception {
		// 로그인하기
		Map<String, String> map = new HashMap<>();
		map.put("UserId", dto.getUserId());
		map.put("UserPwd", dto.getUserPwd());
		
		Member res = memberService.loginMember(map);
		// 로그인 실패 시, 메인페이지로 이동
		if(res == null) {
			reAttr.addFlashAttribute("message", "아이디나 비밀번호가 일치하지 않습니다.");
			
			return "redirect:/board/main";
		}
		
		// 세션에 로그인 정보를 저장한다.
		SessionInfo info = new SessionInfo();
		info.setUserId(res.getUserId());
		info.setUserName(res.getUserName());
		info.setUserRole(res.getRole());
		info.setUserEmail(res.getUserEmail());
		
		session.setMaxInactiveInterval(30 * 60);
		session.setAttribute("member", info);
		
		// 로그인 이전 URI 이동
		String uri = (String) session.getAttribute("preLoginURI");

		session.removeAttribute("preLoginURI");
		if(uri == null) {
			uri = "redirect:/board/main";
		} else {
			uri = "redirect:" + uri;
		}
		
		return uri;
	}
	
	
	// 로그아웃
	@RequestMapping(value = "logout")
	public String logout(HttpSession session, RedirectAttributes reAttr) {
		// 세션 정보를 삭제
		session.removeAttribute("member");
		session.invalidate();
				
		reAttr.addFlashAttribute("message", "로그아웃이 완료되었습니다.");
		
		return "redirect:/board/main";
	}
	
	// 회원가입 get
	@RequestMapping(value = "signUp", method=RequestMethod.GET)
	public String signUpForm(Model model) {
		model.addAttribute("mode", "signUp");
		
		return "member/member";
	}
	
	// 회원가입 post
	@RequestMapping(value = "signUp", method=RequestMethod.POST)
	public String signUpSubmit(Member dto, Model model, final RedirectAttributes reAttr) {
		try {
			memberService.insertMember(dto);
		} catch (Exception e) {
		}
		
		reAttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
		
		return "redirect:/board/main";
	}
	
	
	// 아이디중복확인(ajax)
	@RequestMapping(value = "idcheck", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> idCheck(@RequestParam String id) throws Exception {
		String p = "true";
		Member dto = memberService.readMember(id);
		if(dto != null) {
			p = "false";
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("res", p);
		
		return model;
	}
	
	
	// 회원정보수정 get
	@RequestMapping(value = "update", method=RequestMethod.GET)
	public String updateForm(HttpSession session, Model model) {
		if(session.getAttribute("member") == null)
			return "redirect:/board/main";
		
		model.addAttribute("mode", "update");
		model.addAttribute("dto", session.getAttribute("member"));
		
		return "member/member";
	}
	
	// 회원정보수정 post
	@RequestMapping(value = "update", method=RequestMethod.POST)
	public String updateSubmit(
			Member dto, 
			HttpSession session,
			final RedirectAttributes reAttr) {
		try {
			// 정보수정일과 수정된 정보로 업데이트 후, 로그아웃까지 해주기
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd");
			
			dto.setModifiedDate(sdf.format(now).toString());
			memberService.updateMember(dto);
			logout(session, reAttr);
		} catch (Exception e) {
		}
		
		reAttr.addFlashAttribute("message", "회원정보수정이 완료되었습니다.");
		return "redirect:/board/main";
	}
	
	
	// 회원탈퇴
	@RequestMapping(value = "delete")
	public String delete(
			Member dto, 
			HttpSession session,
			final RedirectAttributes reAttr) {
		try {
			memberService.deleteMember(dto.getUserId());
			logout(session, reAttr);
		} catch (Exception e) {
		}
		
		reAttr.addFlashAttribute("message", "회원탈퇴가 완료되었습니다.");
		return "redirect:/board/main";
	}
	
	
}
