package com.sist.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.dao.BoardRepository;
import com.sist.web.entity.BoardEntity;
import com.sist.web.vo.BoardUpdateVO;
import com.sist.web.vo.BoardVO;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.matcher.ModifierMatcher.Mode;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardRepository boardRepository;
	
	@GetMapping("/board/list")
	public String board_list(@RequestParam(name = "page", required = false) String page, Model model) {
		if(page == null) {
			page = "1";
		}
		
		int curpage = Integer.parseInt(page);
		int rowSize = 10;
		int start = (rowSize * curpage) - (rowSize - 1);
		int end = rowSize * curpage;
		
		List<BoardVO> list = boardRepository.boardListData(start, end);
		int count = (int) boardRepository.count();
		int totalpage = (int) (Math.ceil(count / 10.0));
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		
		model.addAttribute("main_html", "board/list");
		
		return "main/main";
	}
	
	@GetMapping("/board/insert")
	public String board_insert(Model model) {
		model.addAttribute("main_html", "board/insert");
		return "main/main";
	}
	
	@PostMapping("/board/insert_ok")
	public String board_insert_ok(@ModelAttribute("vo") BoardEntity vo) {
		vo.setNo(boardRepository.getMax());
		vo.setHit(0);
		vo.setRegdate(new Date());
		boardRepository.save(vo);
		return "redirect:/board/list";
	}
	
	@GetMapping("/board/detail")
	public String board_detail(@RequestParam("no") int no, Model model) {
		BoardEntity vo = boardRepository.findByNo(no);
		// 조회수 증가
		vo.setHit(vo.getHit() + 1);
		boardRepository.save(vo);
		
		model.addAttribute("vo", vo);
		model.addAttribute("main_html", "board/detail");
		
		return "main/main";
	}
	
	@GetMapping("/board/update")
	public String board_update(@RequestParam("no") int no, Model model) {
		BoardUpdateVO vo = boardRepository.boardUpdateData(no);
		model.addAttribute("vo", vo);
		model.addAttribute("main_html", "board/update");
		return "main/main";
	}
	
	@PostMapping("/board/update_ok")
	public String board_update_ok(@ModelAttribute("vo") BoardEntity vo, Model model) {
		BoardEntity dbVO = boardRepository.findByNo(vo.getNo());
		String res = "no";
		if(vo.getPwd().equals(dbVO.getPwd())) {
			vo.setNo(vo.getNo());
			vo.setHit(dbVO.getHit());
			boardRepository.save(vo);
			res = "yes";
		}
		model.addAttribute("res", res);
		model.addAttribute("no", vo.getNo());
		return "board/update_ok";
	}
	
	@GetMapping("/board/delete")
	public String board_delete(@RequestParam("no") int no, Model model) {
		model.addAttribute("no", no);
		model.addAttribute("main_html", "board/delete");
		return "main/main";
	}
	
	@PostMapping("/board/delete_ok")
	public String board_delete_ok(@RequestParam("no") int no, @RequestParam("pwd") String pwd, Model model) {
		String res = "no";
		BoardEntity vo = boardRepository.findByNo(no);
		if(pwd.equals(vo.getPwd())) {
			res = "yes";
			boardRepository.delete(vo);
		}
		
		model.addAttribute("res", res);
		
		return "board/delete_ok";
	}
	
}
