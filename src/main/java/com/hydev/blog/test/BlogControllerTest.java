package com.hydev.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //스프링이 com.hydev.blog 패키지 이하를 스캔해서 모든 file을 메모리에 new하는건 아니고
					 //특정 어노테이션이 붙어있는 클래스 파일들을 new 해서(IoC) 스프링 컨테이너에서 관리해줌
public class BlogControllerTest {
	
	//http://localhost:8098/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>Hello Spring Boot</h1>";
	}
	
}
