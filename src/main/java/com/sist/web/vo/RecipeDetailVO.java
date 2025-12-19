package com.sist.web.vo;

import lombok.Data;

@Data
public class RecipeDetailVO {
	private int no;
	private String poster, title, chef, chef_profile, chef_poster,
		info1, info2, info3, content, foodmake;
}
