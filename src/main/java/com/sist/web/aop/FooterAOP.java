package com.sist.web.aop;

import java.util.List;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sist.web.service.FoodService;
import com.sist.web.service.RecipeService;
import com.sist.web.vo.FoodVO;
import com.sist.web.vo.RecipeVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/*
 * 		AOP 적용 => JoinPoint / PointCut
 * 								어떤 메소드인지
 * 					메소드 어느 영역인지
 * 
 * 					public String aaa() {
 * 						try {
 * 							----------------------- @Aroun
 * 
 * 						
 * 						} catch (Exception e) {
 * 							@After-Throwing
 * 						} finally {
 * 						}
 * 						return ""; @After - Returning
 * 					해당 메소드를 한번에 통합해서 => 호출
 * 					-------------------------- 위빙
 * 
 *  				@After @Before @After-Throwing
 *  				after
 */

@Aspect // 공통 모듈 => 모든 HTML에 공통으로 적용
@Component
@RequiredArgsConstructor
public class FooterAOP {
	private final FoodService fService; // 싱글턴으로 저장되어 다른 곳에서 사용한 fservice와 같다
	private final RecipeService rService;
	
	@After("execution(* com.sist.web.controller.*Controller.*(..))")
	public void after() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		List<FoodVO> fList = fService.foodTop10Data();
		request.setAttribute("fList", fList);
		List<RecipeVO> rList = rService.recipeTop10();
		request.setAttribute("rList", rList);
	}
}
