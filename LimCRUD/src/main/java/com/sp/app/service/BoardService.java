package com.sp.app.service;

import org.springframework.data.domain.Page;

import com.sp.app.domain.Board;

public interface BoardService {
	public void insertBoard(Board entity) throws Exception;
	
	public Page<Board> listPage(String condition, String keyword, int current_page, int size);
	
	public Board readBoard(int num);
	public Board preReadBoard(Board entity) throws Exception;
	public Board nextReadBoard(Board entity) throws Exception;
	
	public void updateBoard(Board entity) throws Exception;
	public void deleteBoard(int num) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
}
