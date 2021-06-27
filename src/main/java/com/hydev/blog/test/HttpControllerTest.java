package com.hydev.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//만약 사용자 요청시 HTML파일을 응답해주는거 하고싶으면 그냥 Controller하면 됨

// 사용자가 요청할때 거기에 대한 데이터를 응답 해주는 controller

@RestController
public class HttpControllerTest {

	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder()
												
												.username("홍홍")
												.password("1234")
												.email("sswq@naver.com")
												.build();
		System.out.println(TAG+"Getter : "+m.getUsername());
		m.setUsername("KAO");
		System.out.println(TAG+"Getter : "+m.getUsername());
		return "lombok test 완료";
	}
	// http://localhost:8098/http/get
	// 인터넷 브라우저 요청은 무조건 get만!!
	
	// 이렇게 한개한개 받을 수도 있고
	/*
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id,@RequestParam String username) {
		return "get 요청 : "+id+", "+username;
	}
	*/
	
	@GetMapping("/http/get")
	public String getTest(Member m) {// id=1&username=ssar&password=1234&email=ssar@nate.com
		
		
		return "get 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8098/http/post
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8098/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8098/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
