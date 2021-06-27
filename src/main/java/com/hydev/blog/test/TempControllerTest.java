package com.hydev.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	//localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//contorller 어노가 붙어있으면 파일을 리턴하라는 뜻
		//파일 리턴 기본 경로 : src/main/resources/static 이게 기본 경로
		//근데 그냥 하면 src/main/resources/statichome.html 이렇게 리턴하느거
		// 리턴명 : /home.html 이렇게 해야함
		// 풀경로 : src/main/resources/static/home.html
		
		//String 그 문자 자체를 리턴한 restcontroller랑은 다름
		return "/home.html";
	}
	
	@GetMapping("temp/jsp")
	public String tempJsp() {
		// 그래서 이제 동작은 
		// prefix : /WEB-INF/views/
		// suffix : .jsp
		// 풀테임 : /WEB-INF/views//test.jsp.jsp 이런식으로 되서 바꿔줘야함
		
		//톰캣이 알아서 해당 파일 컴파일해서 html로 던져주게되는거!
		return "test";
	}
	
}
