package com.hydev.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hydev.blog.model.User;
import com.hydev.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록해줌 (IoC)
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 회원가입(User user) {
		userRepository.save(user);
	}
	
}
