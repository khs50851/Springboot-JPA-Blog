package com.hydev.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hydev.blog.dto.ReplySaveRequestDto;
import com.hydev.blog.model.Board;
import com.hydev.blog.model.Reply;
import com.hydev.blog.model.RoleType;
import com.hydev.blog.model.User;
import com.hydev.blog.repository.BoardRepository;
import com.hydev.blog.repository.ReplyRepository;
import com.hydev.blog.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board,User user) { // title,content 받음
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){ // 이렇게 페이징 호출
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 글 번호를 찾을 수 없습니다."+id);
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id,Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글수정 실패 : 아이디를 찾을 수 없습니다. id : "+id);
				});
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시 트랜잭션이 Service가 종료될때 트랜잭션이 종료되면서 더티체킹이 일어나면서 자동 업데이트
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto requestReply) {
		
		/* 20210713 첫번째 방법
		User user = userRepository.findById(requestReply.getUserid())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다.");
				}); // 영속화 완료
		
		Board board = boardRepository.findById(requestReply.getBoardid())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
				}); // 영속화 완료
		
		Reply reply = Reply.builder()
				.user(user)
				.board(board)
				.content(requestReply.getContent())
				.build();
				
		*/		
		int result = replyRepository.mSave(requestReply.getUserid(),requestReply.getBoardid(),requestReply.getContent());
		System.out.println("결과 : "+result);
	}
	
	@Transactional
	public void 댓글삭제(int replyid) {
		replyRepository.deleteById(replyid);
	}
	
}
