package com.sist.web.vo;

import lombok.Data;

@Data
public class RecipeVO {
	private int no, hit, likecount, jjimcount, replycount;
	private String title, poster, chef, link;
}
 