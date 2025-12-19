package com.sist.web.vo;

import java.sql.Date;

public interface BoardVO {
	public int getNo();
	public String getName();
	public String getSubject();
	public String getContent();
	public Date getRegdate();
	public String getDbday();
	public int getHit();
	public int getNum();
}
