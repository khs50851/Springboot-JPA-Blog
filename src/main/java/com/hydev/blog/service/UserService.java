package com.hydev.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hydev.blog.model.RoleType;
import com.hydev.blog.model.User;
import com.hydev.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록해줌 (IoC)
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	

	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화를 시키고 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로 부터 가져오는 이유 : 영속화 하기 위해
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 업데이트문을 날려줌
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패 id가 없습니다. id : "+user.getId());
		});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		// 회원 수정 함수 종료시 = 서비스 종료
		
	}
	
	
	/* 전통적인 로그인 방식
	@Transactional(readOnly = true) // 이렇게 하면 select 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	*/
}
