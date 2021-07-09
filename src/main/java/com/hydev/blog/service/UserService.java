package com.hydev.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	
	/* 전통적인 로그인 방식
	@Transactional(readOnly = true) // 이렇게 하면 select 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	*/
}
