package com.hydev.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 Exception이 발생하면 이 클래스로 들어옴
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=IllegalArgumentException.class) // IllegalArg~ 에러 발생하면 여기로 스프링이 전달해줌
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>"+e.getMessage()+"</h1>";
	}
	
	// 다른 Exception발생하면 다른거추가하면 되고 모든걸 다 받고싶으면 가장 최상위인 Exception으로 하면 됨
	
	
}

