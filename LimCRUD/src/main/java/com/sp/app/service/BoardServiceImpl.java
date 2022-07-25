package com.sp.app.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sp.app.domain.Board;
import com.sp.app.repository.BoardRepository;

@Service("board.boardService")
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	// 게시글 등록
	@Override
	public void insertBoard(Board dto) throws Exception {
		try {
			boardRepository.save(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	// 리스트 페이지
	@Override
	public Page<Board> listPage(String keyword, int current_page, int size) {
		Page<Board> page = null;
		
		try {
			Pageable pageable = PageRequest.of(current_page-1, size,
					Sort.by(Sort.Direction.DESC, "num"));
			
			if(keyword.length() == 0) {
				page = boardRepository.findAll(pageable);
			} else {
				page = boardRepository.findByAll(keyword, pageable);
			}
		} catch (IllegalArgumentException e) {
			// 데이터가 존재하지 않는 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}

	
	// 게시글 상세
	@Override
	public Board readBoard(long num) {
		Board dto = null;
		
		try {
			// Optional클래스는 null값에 대한 처리를 좀 더 깔끔하게 할 수 있도록 도와준다.
			Optional<Board> board = boardRepository.findById(num);
			dto= board.get();
		} catch (NoSuchElementException e) {
			// 데이터 없으면 오류
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	

	
	// 게시글 이전글
	@Override
	public Board preReadBoard(Board entity) throws Exception {

		return null;
	}

	
	// 게시글 다음글
	@Override
	public Board nextReadBoard(Board entity) throws Exception {

		return null;
	}

	
	// 게시글 수정
	@Override
	public void updateBoard(Board entity) throws Exception {
		try {
			boardRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	
	// 게시글 조회수수정
	@Override
	public void updateHitCount(long num) throws Exception {
		try {
			boardRepository.updateHitCount(num);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	
	// 게시글 삭제
	@Override
	public void deleteBoard(long num) throws Exception {
		try {
			boardRepository.deleteById(num);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	
}
