package com.hydev.blog.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hydev.blog.config.auth.PrincipalDetail;
import com.hydev.blog.dto.ReplySaveRequestDto;
import com.hydev.blog.dto.ResponseDto;
import com.hydev.blog.model.Board;
import com.hydev.blog.model.Reply;
import com.hydev.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board,@AuthenticationPrincipal PrincipalDetail principal) {
		boardService.글쓰기(board,principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id,@RequestBody Board board){
		boardService.글수정하기(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	// 원래는 데이터를 받을 때 컨트롤러에서 dto를 만들어 받는게 조음
	
	@PostMapping("/api/board/{boardid}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto reply) {
		
		
		boardService.댓글쓰기(reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("/api/board/{boardid}/reply/{replyid}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyid){
		boardService.댓글삭제(replyid);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
}
