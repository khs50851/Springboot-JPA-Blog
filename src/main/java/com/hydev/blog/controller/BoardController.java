package com.hydev.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hydev.blog.config.auth.PrincipalDetail;
import com.hydev.blog.service.BoardService;

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
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"","/"})
	public String index(Model model) { // 컨트롤러에서 세션을 어떻게 찾는지?
		/// WEB-INF/views/index.jsp
		model.addAttribute("boards",boardService.글목록());
		return "index"; // 이건 그냥 컨트롤러라 리턴할때 viewResolver가 작동함 작동하면 해당 index페이지로 이 model의 정보를 들고 이동함, 그리고 이 뷰리졸버는 이 리턴값 index에 앞뒤로 prefix,suffix를 붙여줌 그래서 index만 적으면 됨 이 model은 jsp에서 request정보라고 생각하면 됨
	}
	
	// USER 권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
			return "board/saveForm";
	}
}
