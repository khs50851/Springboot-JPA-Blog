package com.hydev.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// 요 클래스가 bean으로 등록이 되어야하는데 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는거
@Configuration // 이렇게 하면 빈으로 등록됨

//시큐리티가 모든 리퀘스트를 가로챔 예를들면 컨트롤러로 가기전에 얘가 가로채서 아래 auth 밑이면 다 들어오게 하고 아니면 필터링함
@EnableWebSecurity // 이렇게 필터를 걸음 시큐리티 필터 추가 = 스프링 시큐리티가 활성화 되어있는데 어떤 설정을 해당 파일(securityConfig)에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 밑 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests() //request가 들어오면
				.antMatchers("/auth/**") // auth쪽으로 들어오는건 누구나 들어올수있다
				.permitAll()
				.anyRequest() // 이게 아닌 다른 모든 요청은
				.authenticated() // 인증이 되어야 한다
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");  // 이렇게 하면 로그인 폼이 자동으로 뜸
		
	}
}
