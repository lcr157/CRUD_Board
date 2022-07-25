package com.sp.app.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sp.app.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	// JPQL
	
	// 전체(all)로 리스트 가져오기
	@Query(
			value = "SELECT b FROM Board b WHERE b.Subject LIKE %:keyword% OR b.Content LIKE %:keyword%",
			countQuery = "SELECT COUNT(b.num) FROM Board b WHERE b.Subject LIKE %:keyword% OR b.Content LIKE %:keyword%"
	)
	public Page<Board> findByAll(@Param("keyword") String keyword, Pageable pageable);
	
	
	// 조회수 업데이트
	@Transactional	// 중간에 에러터지면 모든 작업을 원상태로 되돌림
	@Modifying		// insert, update, delete 쿼리 메소드에 필요하다
	@Query(
			value = "UPDATE board SET Hit_Count = Hit_Count + 1 WHERE num=:num",
			nativeQuery = true
	)
	public int updateHitCount(@Param("num") long num);
}
