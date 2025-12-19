package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {
	private Date regdate;
	private int no, cno, type;
	private String id, name, msg, dbday;
}
