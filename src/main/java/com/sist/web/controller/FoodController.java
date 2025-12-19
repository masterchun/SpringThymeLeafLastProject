package com.sist.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.FoodService;
import com.sist.web.vo.FoodVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FoodController {
	private final FoodService fService;
	
	@GetMapping("/food/list")
	public String food_list(@RequestParam(name = "page", required = false) String page, Model model) {
		if(page == null) {
			page = "1";
		}
		int curpage = Integer.parseInt(page);
		int start = (curpage - 1) * 12;
		List<FoodVO> list = fService.foodListData(start);
		int totalpage = fService.foodTotalPage();
		
		final int BLOCK = 10;
		int startPage = ((curpage - 1) / BLOCK * BLOCK) + 1;
		int endPage = ((curpage - 1) / BLOCK * BLOCK) + BLOCK;
		if(endPage > totalpage) {
			endPage = totalpage;
		}
		
		model.addAttribute("list", list);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("curpage", curpage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		model.addAttribute("main_html", "food/list");
		
		return "main/main";
	}
	
	@GetMapping("/food/detail")
	public String food_detail(@RequestParam("fno") int fno, HttpSession session, Model model) {
		FoodVO vo = fService.foodDetailData(fno);
		
		String id = (String) session.getAttribute("id");
		if(id == null) {
			model.addAttribute("sessionId", "");
		} else {
			model.addAttribute("sessionId", id);
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("main_html", "food/detail");
		
		return "main/main";
	}
}































