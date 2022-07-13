package com.sp.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sp.app.service.BoardService;

@Controller
@RequestMapping("/bbs/*")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("list")
	public String list() throws Exception {
		
		
		return "bbs/list";
	}
	
	
	
}
