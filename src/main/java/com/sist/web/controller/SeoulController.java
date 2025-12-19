package com.sist.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.SeoulService;
import com.sist.web.vo.FoodVO;
import com.sist.web.vo.SeoulVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seoul/")
public class SeoulController {
	private final SeoulService sService;
	
	private String[] tables = {
			"",
			"seoul_location",
			"seoul_nature",
			"seoul_shop"
	};
	
	public void seoul_common(String page, Model model, String tbl) {
		if(page == null) {
			page = "1";
		}
		int curpage = Integer.parseInt(page);
		int start = (curpage - 1) * 12;
		
		Map map = new HashMap();
		map.put("start", start);
		map.put("table_name", tbl);
		
		List<SeoulVO> list = sService.seoulListData(map);
		int totalpage = sService.seoulTotalPage(map);
		
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
		
		model.addAttribute("main_html", tbl.replace("_", "/"));
	}
	
	@GetMapping("location")
	public String seoul_location(@RequestParam(name = "page", required = false) String page, Model model) {
		seoul_common(page, model, "seoul_location");
		return "main/main";
	}
	
	@GetMapping("nature")
	public String seoul_nature(@RequestParam(name = "page", required = false) String page, Model model) {
		seoul_common(page, model, "seoul_nature");
		return "main/main";
	}
	
	@GetMapping("shop")
	public String seoul_shop(@RequestParam(name = "page", required = false) String page, Model model) {
		seoul_common(page, model, "seoul_shop");
		return "main/main";
	}
	
	@GetMapping("detail")
	public String seoul_detail(@RequestParam("no") int no, @RequestParam("type") int type, Model model) {
		Map map = new HashMap();
		map.put("no", no);
		map.put("table_name", tables[type]);
		
		SeoulVO vo = sService.seoulDetailData(map);
		String address = vo.getAddress();
		address = address.substring(address.indexOf(" ") + 1);
		vo.setAddress(address.trim());
		model.addAttribute("vo", vo);
		
		String[] addr = vo.getAddress().split(" ");
		String addr1 = addr[1].trim();
		List<FoodVO> list = sService.seoulNearFoodHouse(addr1);
		
		model.addAttribute("type", type);
		model.addAttribute("list", list);
		model.addAttribute("main_html", "seoul/detail");
		
		return "main/main";
	}
}




























