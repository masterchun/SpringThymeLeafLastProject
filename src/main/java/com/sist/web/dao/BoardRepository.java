package com.sist.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.web.entity.BoardEntity;
import com.sist.web.vo.BoardUpdateVO;
import com.sist.web.vo.BoardVO;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	// 상세보기 : 메소드
	public BoardEntity findByNo(int no);
	// SELECT * FROM board WHERE no = 1
	/*
	 * 	findByNameContains %name%
	 * 	findByNameStartsWith name%
	 * 	findByNameEndsWith %name
	 */
	// update, insert, delete, count
	// CLOB을 인식하지 못한다 => TO_CHAR
	@Query(value = "SELECT no, subject, name, TO_CHAR(regdate, 'yyyy-mm-dd') as dbday, hit, num "
		 + "FROM (SELECT no, subject, name, regdate, hit, rownum as num "
		 + "FROM (SELECT no, subject, name, regdate, hit "
		 + "FROM board_3 ORDER BY no DESC)) "
		 + "WHERE num BETWEEN :start AND :end", nativeQuery = true)
	// nativeQuery => SQL 문장을 그대로 적용 JPQL로 변환 => 오류 발생
	// 받는 변수 ?
	public List<BoardVO> boardListData(@Param("start") Integer start, @Param("end") Integer end);
	
	@Query(value = "SELECT NVL(MAX(no)+1, 1) FROM board_3", nativeQuery = true)
	public int getMax();
	
	@Query(value = "SELECT no, subject, name, TO_CHAR(content) as content "
				 + "FROM board_3 WHERE no = :no", nativeQuery = true)
	public BoardUpdateVO boardUpdateData(@Param("no") int no);
}
