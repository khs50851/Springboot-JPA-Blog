package com.hydev.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hydev.blog.model.Board;
import com.hydev.blog.model.RoleType;
import com.hydev.blog.model.User;
import com.hydev.blog.repository.BoardRepository;
import com.hydev.blog.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void 글쓰기(Board board,User user) { // title,content 받음
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	public Page<Board> 글목록(Pageable pageable){ // 이렇게 페이징 호출
		return boardRepository.findAll(pageable);
	}
	
}
