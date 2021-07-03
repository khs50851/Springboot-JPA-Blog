package com.hydev.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hydev.blog.dto.ResponseDto;
import com.hydev.blog.model.RoleType;
import com.hydev.blog.model.User;
import com.hydev.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출 됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 됨.
		user.setRole(RoleType.USER);
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}
