package com.sp.app.service;

import org.springframework.data.domain.Page;

import com.sp.app.domain.Board;

public interface BoardService {
	public void insertBoard(Board entity) throws Exception;
	
	public Page<Board> listPage(String keyword, int current_page, int size);
	
	public Board readBoard(long num);
	public Board preReadBoard(Board dto) throws Exception;
	public Board nextReadBoard(Board dto) throws Exception;
	
	public void updateBoard(Board dto) throws Exception;
	public void deleteBoard(long num) throws Exception;
	
	public void updateHitCount(long num) throws Exception;
}
