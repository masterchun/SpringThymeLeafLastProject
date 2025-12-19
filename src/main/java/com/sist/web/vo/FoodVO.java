package com.sist.web.vo;

import lombok.Data;

@Data
public class FoodVO {
	private double score;
	private int fno, hit, jjimcount, likecount, replycount;
	private String name, address, phone, theme, type, price, time, parking, poster, images, content;
}
