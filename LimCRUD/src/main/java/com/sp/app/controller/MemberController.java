package com.sp.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	@RequestMapping("login")
	public String login() throws Exception {
		
		return "member/login";
	}
	
}
