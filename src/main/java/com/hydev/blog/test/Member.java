package com.hydev.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@Data // Getter Setter 동시에
//@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class Member {
	private int id;
	private String password;
	private String username;
	private String email;
	
	@Builder
	public Member(int id, String password, String username, String email) {
		this.id = id;
		this.password = password;
		this.username = username;
		this.email = email;
	}
	
	
	
	
}
