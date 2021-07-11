package com.hydev.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 왜 auth를 붙이냐면 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 등 허용
// 인증이 필요 없는곳엔 auth를 붙임

@Controller
public class UserController {
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() { // 시큐리티컨텍스트홀더 안에 시큐리티컨텍스트 안에 authentication객체를 가져옴
		return "user/updateForm";
		// 로그인 요청을 하면 authentication 필터를 거치게됨 그래서 http 바디에 username이랑 password를 달고옴 이 두가지를 가지고
		// usernamepasswordauthenticationtoken을 만듬 이걸 갖고 authen~manager라는게(얘는 패스워드 인코딩 확인)  저 토큰을 userdetailservice한테 던져서 service는 db에 username이 있는지 던지고 있으면 authentication객체를 만들고 아니면 안만듬
	}
}
