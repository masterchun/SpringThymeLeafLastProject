package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.RecipeService;
import com.sist.web.vo.RecipeDetailVO;
import com.sist.web.vo.RecipeVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/recipe/")
@RequiredArgsConstructor
public class RecipeController {
	private final RecipeService rService;
	
	@GetMapping("list")
	public String recipe_list(@RequestParam(name = "page", required = false) String page, Model model) {
		if(page == null) {
			page = "1";
		}
		int curpage = Integer.parseInt(page);
		int start = (curpage - 1) * 12;
		
		List<RecipeVO> list = rService.recipeListData(start);
		int totalpage = rService.recipeTotalPage();
		
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
		
		model.addAttribute("main_html", "recipe/list");
		return "main/main";
	}
	
	@GetMapping("detail")
	public String recipe_detail(@RequestParam("no") int no, HttpSession session, Model model) {
		RecipeDetailVO vo = rService.recipeDetailData(no);
		
		List<String> mList = new ArrayList<String>();
		List<String> nList = new ArrayList<String>();
		
		String[] datas = vo.getFoodmake().split("\n");
		for(String s:datas) {
			StringTokenizer st = new StringTokenizer(s, "^");
			mList.add(st.nextToken());
			nList.add(st.nextToken());
		}
		
		model.addAttribute("mList", mList);
		model.addAttribute("nList", nList);
		model.addAttribute("vo", vo);
		
		String id = (String) session.getAttribute("id");
		if(id == null) {
			model.addAttribute("sessionId", "");
		} else {
			model.addAttribute("sessionId", id);
		}
		
		model.addAttribute("main_html", "recipe/detail");
		return "main/main";
	}
	
	   @GetMapping("chef_list")
	   public String recipe_chef_list(
			   @RequestParam(name="page",required = false) String page,
			   @RequestParam("chef") String chef,Model model)
	   {
		   // DB 연동 
		   if(page==null)
		   		  page="1";
			   	int curpage=Integer.parseInt(page);// 현재 페이지
			   	// 현재 페이지의 데이터 읽기
			   	int start=(curpage-1)*12;
			   	
			   	List<RecipeVO> list=rService.recipeChecfListData(start, chef);
			   	// 0 , 12, 24...
			   	// 총페이지 읽기
			   	int totalpage=rService.recipeChefTotalPage(chef);
			   	
			   	final int BLOCK=10;
			   	int startPage=((curpage-1)/BLOCK*BLOCK)+1;
			   	int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
			   	
			   	if(endPage>totalpage)
			   		endPage=totalpage;
			   	
			   	model.addAttribute("list", list);
			   	model.addAttribute("curpage", curpage);
			   	model.addAttribute("totalpage", totalpage);
			   	model.addAttribute("startPage", startPage);
			   	model.addAttribute("endPage", endPage);
			   	model.addAttribute("chef", chef);
		   model.addAttribute("main_html", "recipe/chef_list");
		   return "main/main";
	   }
}































