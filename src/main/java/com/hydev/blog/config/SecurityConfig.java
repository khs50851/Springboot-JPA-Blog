package com.hydev.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hydev.blog.config.auth.PrincipalDetailService;

// 요 클래스가 bean으로 등록이 되어야하는데 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는거
@Configuration // 이렇게 하면 빈으로 등록됨

//시큐리티가 모든 리퀘스트를 가로챔 예를들면 컨트롤러로 가기전에 얘가 가로채서 아래 auth 밑이면 다 들어오게 하고 아니면 필터링함
@EnableWebSecurity // 이렇게 필터를 걸음 시큐리티 필터 추가 = 스프링 시큐리티가 활성화 되어있는데 어떤 설정을 해당 파일(securityConfig)에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 밑 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // IoC
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); // 이 객체를 통해 .encode("1234") 이런식으로 넣으면 암호화됨
	}
	
	// 시큐리티가 대신 로그인을 해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 돼서 회원 가입이 되었는지 알아야
	// 같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); // 이렇게 안하면 해쉬값으로 패스워드 비교를 못함
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests() //request가 들어오면
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // 메인페이지,auth,css,js,image쪽으로 들어오는건 누구나 들어올수있다
				.permitAll()
				.anyRequest() // 이게 아닌 다른 모든 요청은
				.authenticated() // 인증이 되어야 한다
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")  // 이렇게 하면 로그인 폼이 자동으로 뜸
				// 스프링 시큐리티가 로그인 가로채게 할거임
				.loginProcessingUrl("/auth/loginProc") // 이렇게 하면 해당 주소로 요청오는 로그인을 가로채서 대신 로그인
				.defaultSuccessUrl("/"); // 로그인 후 default 페이지로 이동
				//.failureForwardUrl("") 실패하면 여기로 가게 할 수 있음
				
	}
}
