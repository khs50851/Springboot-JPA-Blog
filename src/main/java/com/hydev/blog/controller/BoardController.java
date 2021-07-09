package com.hydev.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.hydev.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	/*
	@GetMapping({"","/"})
	public String index(@AuthenticationPrincipal PrincipalDetail principal) { // 컨트롤러에서 세션을 어떻게 찾는지?
		/// WEB-INF/views/index.jsp
		System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
		return "index";
	}
	*/
	
	@GetMapping({"","/"})
	public String index() { // 컨트롤러에서 세션을 어떻게 찾는지?
		/// WEB-INF/views/index.jsp
		return "index";
	}
	
	// USER 권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
			return "board/saveForm";
	}
}
