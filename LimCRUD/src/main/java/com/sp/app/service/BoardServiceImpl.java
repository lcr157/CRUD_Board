package com.sp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sp.app.domain.Board;
import com.sp.app.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public void insertBoard(Board entity) throws Exception {
		try {
			boardRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Page<Board> listPage(String condition, String keyword, int current_page, int size) {

		return null;
	}

	@Override
	public Board readBoard(int num) {

		return null;
	}

	@Override
	public Board preReadBoard(Board entity) throws Exception {

		return null;
	}

	@Override
	public Board nextReadBoard(Board entity) throws Exception {

		return null;
	}

	@Override
	public void updateBoard(Board entity) throws Exception {


	}

	@Override
	public void deleteBoard(int num) throws Exception {

		
	}

	@Override
	public void updateHitCount(int num) throws Exception {

		
	}
	
}
