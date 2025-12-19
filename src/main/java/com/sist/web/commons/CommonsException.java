package com.sist.web.commons;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonsException {
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("============== EXCEPTION 발생 ==============");
		e.printStackTrace();
	}
	
	@ExceptionHandler(Throwable.class)
	public void throwable(Throwable e) {
		System.out.println("============== Throwable 발생 ==============");
		e.printStackTrace();
	}
}
