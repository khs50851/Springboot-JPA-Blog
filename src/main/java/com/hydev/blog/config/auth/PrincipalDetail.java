package com.hydev.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hydev.blog.model.User;

import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해줌.
@Getter
public class PrincipalDetail implements UserDetails{
	
	private User user; // 컴포지션 (객체를 품고 있음)

	public PrincipalDetail(User user) { // 이렇게 생성자로 저장 안해주면 PrincipalDetailService에 load~ 함수에서 리턴할때 principal 값이 그냥 user에 password가 서버 키면 나오는 그 패스워드로 들어감 
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다. (true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정이 잠겨있는지 안잠겨있는지 (true:안잠겨있음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비번이 만료되었는지 안되었는지(true:만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정 활성화가 되어있는지 안되어있는지 (true:활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정의 권한 리턴
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				return "ROLE_"+user.getRole(); // 스프링에서 Role을받을때 규칙임 ROLE_ 이거 꼭 붙여야함 리턴은 ROLE_USER 이런식으로 될거임
//			}
//		});
//		return collectors;
//	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{return "ROLE_"+user.getRole();}); // 어차피 이건 메소드를 하나만 갖고있어서 그냥 람다식으로 표현 권한이 여러개면 for문 돌림
		return collectors;
	}
	
}
