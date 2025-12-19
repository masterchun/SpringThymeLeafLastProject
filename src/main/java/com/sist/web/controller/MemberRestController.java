package com.sist.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.MemberService;
import com.sist.web.vo.MemberVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberRestController {
	private final MemberService mService;
	
	@GetMapping("/member/login_vue/")
	public MemberVO member_login_vue(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpSession session) {
		MemberVO vo = mService.memberLogin(id, pwd);
		if(vo.getMsg().equals("OK")) {
			session.setAttribute("id", vo.getId());
			session.setAttribute("name", vo.getName());
			session.setAttribute("address", vo.getAddress());
		}
		
		return vo;
	}
	
	@GetMapping("/member/logout_vue/")
	public String member_logout_vue(HttpSession session) {
		String res = "yes";
		session.invalidate();
		return res;
	}
}
