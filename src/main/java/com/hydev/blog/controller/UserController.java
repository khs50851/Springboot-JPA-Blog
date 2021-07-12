package com.hydev.blog.controller;

import java.util.UUID;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydev.blog.model.KakaoProfile;
import com.hydev.blog.model.OAuthToken;
import com.hydev.blog.model.User;
import com.hydev.blog.service.UserService;

// 왜 auth를 붙이냐면 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 등 허용
// 인증이 필요 없는곳엔 auth를 붙임


@Controller
public class UserController {
	
	@Value("${hydev.key}")
	private String hyKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수 RestController가 된다는 뜻인듯
		
		// POST방식으로 key=value 데이터를 요청(카카오쪽으로) RestTemplate이 라이브러리로 http 요청을 할 수 있다
		// a태그를 통해 전달하는건 무조건 get방식임
		RestTemplate rt = new RestTemplate();
		
		//http header 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // 요청방식이 form타입이라고 알려주는거임 이렇게 헤드에 담아서
		
		// 데이터 4개 요청할것들을 MultivalueMap 객체 만들어서 하나씩 담음  body 데이터임
		// http body 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "2fb335d9a58248224864df6f5a74c0d1");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// 헤더와 바디를 한번에 담음
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,headers); // 이렇게하면 body데이터와 header를 갖고있는 엔티티가됨
		
		// 실제 요청
		//exchange라는 함수가 HttpEntity라는 오브젝트를 넣게 되어있음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",HttpMethod.POST,kakaoTokenRequest,String.class); // 토큰발급 요청 주소, 요청 메소드, 데이터,응답받을 타입이렇게 하면 String으로 응답받음 
		
		// Gson,Json Simple, ObjectMapper JSON 데이터 받기
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		//http header 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // 요청방식이 form타입이라고 알려주는거임 이렇게 헤드에 담아서
		
		// 헤더와 바디를 한번에 담음
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		
		// 실제 요청
		//exchange라는 함수가 HttpEntity라는 오브젝트를 넣게 되어있음
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me",HttpMethod.POST,kakaoProfileRequest2,String.class); // 토큰발급 요청 주소, 요청 메소드, 데이터,응답받을 타입이렇게 하면 String으로 응답받음 
		
		
		// Gson,Json Simple, ObjectMapper JSON 데이터 받기
				ObjectMapper obMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;
				try {
					kakaoProfile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} 
		// User 오브젝트 : username,password,email
		System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
		System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그 서버 유저네임(카카오) : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘 쓰면 로그인할때마다 패스워드가 계속 바뀜
		//UUID garbagePassword = UUID.randomUUID(); // 그냥 안쓰는 패스워드 임시로 만들어서 걍 집어넣음
		//System.out.println("블로그서버 패스워드 : "+garbagePassword);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(hyKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 가입자 혹은 비가입자 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		System.out.println("22222222222222");
		if(originUser.getUsername() == null) {
			System.out.println("기존회원아 아니기에 회원가입 시작합니다.");
			userService.회원가입(kakaoUser);
		}
		
		System.out.println("자동로그인을 진행합니다.");
		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), hyKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("자동로그인을 완료");
		return "redirect:/";
	}
	
	
	@GetMapping("/user/updateForm")
	public String updateForm() { // 시큐리티컨텍스트홀더 안에 시큐리티컨텍스트 안에 authentication객체를 가져옴
		return "user/updateForm";
		// 로그인 요청을 하면 authentication 필터를 거치게됨 그래서 http 바디에 username이랑 password를 달고옴 이 두가지를 가지고
		// usernamepasswordauthenticationtoken을 만듬 이걸 갖고 authen~manager라는게(얘는 패스워드 인코딩 확인)  저 토큰을 userdetailservice한테 던져서 service는 db에 username이 있는지 던지고 있으면 authentication객체를 만들고 아니면 안만듬
	}
}
