package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.CommentMapper;
import com.sist.web.vo.CommentVO;

import lombok.RequiredArgsConstructor;

@Service // BI (Buisness Integration) => 여러개 DAO 통합해서 사용, SQL 문장 통합
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentMapper mapper;

	@Override
	public List<CommentVO> commentListData(int cno, int type) {
		// TODO Auto-generated method stub
		return mapper.commentListData(cno, type);
	}

	@Override
	public void commentInsert(CommentVO vo) {
		// TODO Auto-generated method stub
		mapper.commentInsert(vo);
	}

	@Override
	public void commentUpdate(String msg, int no) {
		// TODO Auto-generated method stub
		mapper.commentUpdate(msg, no);
	}
	
	@Override
	public void commentDelete(int no) {
		// TODO Auto-generated method stub
		mapper.commentDelete(no);
	}
	
}
