package com.sp.app.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp.app.domain.Board;
import com.sp.app.service.BoardService;

@Controller("board.boardController")
@RequestMapping("/board/*")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// 메인페이지이자 리스트 출력
	@RequestMapping("main")
	public String main(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			Model model) throws Exception {
	
		int rows = 10;
		int total_page = 0;
		long dataCount = 0;
		
		List<Board> list = null;
		
		try {
			if(req.getMethod().equalsIgnoreCase("get")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			Page<Board> pageBoard = boardService.listPage(keyword, current_page, rows);
			
			
			
			total_page = pageBoard.getTotalPages();
			if(current_page > total_page) {
				current_page = total_page;
				pageBoard = boardService.listPage(keyword, current_page, rows);
			}
			
			dataCount = pageBoard.getTotalElements();
			
			list = pageBoard.getContent();
			for(Board dto : list) {
				dto.setRegDate(dto.getRegDate().substring(0, 10));
			}
			
		} catch (Exception e) {
		}
		
		model.addAttribute("list", list);
		model.addAttribute("page", current_page);
		model.addAttribute("rows", rows);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		
		model.addAttribute("keyword", keyword);
		
		return "main/main";
	}
	
	
	// 글쓰기 get
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(HttpSession session, Model model) throws Exception {
		if(session.getAttribute("member") == null) {
			return "member/login";
		}
		model.addAttribute("mode", "write");
		
		return "board/write";
	}
	
	
	// 글쓰기 post
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Board dto, Model model) throws Exception {
		try {
			boardService.insertBoard(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/board/main";
	}
	
	
	// 게시글 상세
	@RequestMapping(value = "article", method = RequestMethod.GET)
	public String articleForm(
			@RequestParam long num,
			@RequestParam String page,
			@RequestParam(defaultValue = "") String keyword,
			Model model) throws Exception {
		
		keyword = URLDecoder.decode(keyword, "utf-8");
		
		// 리스트에서의 페이지와 검색 기록 여부를 query에 저장 
		String query = "page="+page;
		if(keyword.length() != 0) {
			query += "&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		// 조회수 증가
		boardService.updateHitCount(num);
		
		// 회원정보 읽어오기
		Board dto = boardService.readBoard(num);
		if(dto == null) {
			return "redirect:/board/main";
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		model.addAttribute("dto", dto);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		return "board/article";
	}
	
	
	// 게시글 수정 GET
	@RequestMapping(value="update", method = RequestMethod.GET)
	public String updateForm(
			@RequestParam long num,
			@RequestParam String page,
			Model model) throws Exception {
		
		Board dto = boardService.readBoard(num);
		if(dto == null) {
			return "redirect:/board/main?page="+page;
		}
		
		model.addAttribute("mode", "update");
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
				
		return "board/write";
	}
	
	
	// 게시글 수정 POST
	@RequestMapping(value="update", method = RequestMethod.POST)
	public String updateSubmit(
			Board dto,
			@RequestParam String page
			) throws Exception {
		
		try {
			boardService.updateBoard(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/board/main?page="+page;
	}

	
	// 게시글 삭제
	@RequestMapping(value="delete", method = RequestMethod.GET)
	public String delete(@RequestParam int num) {
		try {
			boardService.deleteBoard(num);
		} catch (Exception e) {
		}
		
		return "redirect:/board/main";
	}
	
	
}
