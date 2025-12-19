package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.CommentVO;

@Mapper
@Repository
public interface CommentMapper {
	@Select("SELECT no, cno, type, id, name, msg, "
		  + "TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS') as dbday "
		  + "FROM comment_3 "
		  + "WHERE cno = #{cno} AND type = #{type} "
		  + "ORDER BY no DESC")
	public List<CommentVO> commentListData(@Param("cno") Integer cno, @Param("type") Integer type);
	
	// SEQUENCE => before : 먼저 수행
	@SelectKey(keyProperty = "no", resultType = int.class, 
			before = true, statement = "SELECT NVL(MAX(no) + 1, 1) FROM comment_3")
	@Insert("INSERT INTO comment_3 VALUES("
		  + "#{no}, #{cno}, #{type}, #{id}, #{name}, #{msg}, SYSDATE)")
	public void commentInsert(CommentVO vo);
	
	@Update("UPDATE comment_3 SET "
		  + "msg = #{msg} "
		  + "WHERE no = #{no}")
	public void commentUpdate(@Param("msg") String msg, @Param("no") Integer no);
	
	@Delete("DELETE FROM comment_3 "
		  + "WHERE no = #{no}")
	public void commentDelete(Integer no);
}
