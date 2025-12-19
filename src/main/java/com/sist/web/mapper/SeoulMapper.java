package com.sist.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.FoodVO;
import com.sist.web.vo.SeoulVO;

@Mapper
@Repository
public interface SeoulMapper {
	@Select("SELECT no, title, poster, msg, address, rownum "
		  + "FROM ${table_name} "
		  + "WHERE rownum <= 3 "
		  + "ORDER BY no DESC")
	public List<SeoulVO> seoulMainData(Map map);
	
	// 명소 / 자연 / 쇼핑 => 맛집 => 지도 => 댓글 (Vue)
	@Select("SELECT no, title, poster, address "
		  + "FROM ${table_name} "
		  + "ORDER BY no ASC "
		  + "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<SeoulVO> seoulListData(Map map);
	
	@Select("SELECT CEIL(COUNT(*) / 12.0) FROM ${table_name}")
	public int seoulTotalPage(Map map);
	
	@Update("UPDATE ${table_name} SET "
		  + "hit = hit + 1 "
		  + "WHERE no = #{no}")
	public void seoulHitIncrement(Map map);
	
	@Select("SELECT * FROM ${table_name} "
		  + "WHERE no = #{no}")
	public SeoulVO seoulDetailData(Map map);
	
	@Select("SELECT fno, name, poster, address, rownum "
		  + "FROM menupan_food "
		  + "WHERE REGEXP_LIKE(address, #{adress}) "
		  + "AND rownum <= 6")
	public List<FoodVO> seoulNearFoodHouse(String address);
}
