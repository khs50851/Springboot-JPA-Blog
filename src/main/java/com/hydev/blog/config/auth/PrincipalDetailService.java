package com.hydev.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hydev.blog.model.User;
import com.hydev.blog.repository.UserRepository;

@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired 
	private UserRepository userRepository;
	
	// 스프링에서 로그인 요청을 가로챌때 username,password 변수 2개를 가로채는데
	// password 부분은 알아서 함
	// username이 DB에 있는지만 확인 해주면 됨 이 확인을 loaduserbyusername 함수에서 함
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{ 
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : "+username);
				});
		System.out.println("principal username: "+principal.getUsername()); 
		System.out.println("principal password: "+principal.getPassword());
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장 타입은 UserDetails 타입
	}
}
