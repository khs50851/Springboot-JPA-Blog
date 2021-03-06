package com.hydev.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hydev.blog.config.auth.PrincipalDetail;
import com.hydev.blog.dto.ResponseDto;
import com.hydev.blog.model.RoleType;
import com.hydev.blog.model.User;
import com.hydev.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
//	@Autowired
//	private HttpSession session; // 세션 객체는 스프링 컨테이너가 빈으로 등록해놔서 필요하면 DI해서 사용 가능
	
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출 됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 됨.
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	/* 20210711 연습 내용
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user,@AuthenticationPrincipal PrincipalDetail principal,HttpSession session){
		System.out.println("email : "+user.getEmail());
		userService.회원수정(user);
		
	
		// 여기서 세션 변경할건데 authentication 객체를 직접 만들거임
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null,principal.getAuthorities()); //authen 토큰 생성
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication); // 이렇게 강제로 세션값 바꿈
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext); // 세션 저장
		
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	*/
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user,@AuthenticationPrincipal PrincipalDetail principal,HttpSession session){
		System.out.println("email : "+user.getEmail());
		userService.회원수정(user);
		// 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	// 전통적인 로그인 방식
	/* HttpSession 함수 매개변수로 받아서 사용하는거
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user,HttpSession session){
		System.out.println("UserApiController : login 호출 됨");
		User principal = userService.로그인(user); // principal = 접근 주체
		
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	*/
	
	/* HttpSession DI로 끌어다 쓰는거
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user){
		System.out.println("UserApiController : login 호출 됨");
		User principal = userService.로그인(user); // principal = 접근 주체
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	 */
	
	// 스프링 시큐리티가 설치가 되면 어떤 페이지로 가던지 로그인페이지로 연결됨
	// 처음 아이디 user 
	
}
