package com.hydev.blog;

import org.junit.Test;

import com.hydev.blog.model.Reply;

public class ReplyObjectTest {
	
	@Test
	public void 투스트링테스트() {
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("안녕")
				.build();
		
		System.out.println(reply); // 오브젝트 출력시 toString 자동 호출
	}
}
