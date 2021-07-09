package com.hydev.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.hydev.blog.dto.ResponseDto;

@ControllerAdvice // 모든 Exception이 발생하면 이 클래스로 들어옴
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=Exception.class) // IllegalArg~ 에러 발생하면 여기로 스프링이 전달해줌
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
	}
	
	// 다른 Exception발생하면 다른거추가하면 되고 모든걸 다 받고싶으면 가장 최상위인 Exception으로 하면 됨
	
	
}

