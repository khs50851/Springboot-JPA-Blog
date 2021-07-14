package com.hydev.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hydev.blog.model.Board;
import com.hydev.blog.repository.BoardRepository;

@RestController
public class ReplyControllerTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get(); // 잭슨 라이브러리가 모델의 getter를 호출 근데 getReplys를 리턴하면 그 안에 board가 다시 있고 그럼 또 다시 getboard를 리턴하고 또 그 getboard안에서 또 getReply를 리턴하고 이렇게 계속 왔다갔다 거리면서 무한참조가 일어남
	}
}
