package com.hydev.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User 클래스가 스프링부트가 실행될때 이 클래스를 읽어서 밑에 필드들을 읽어서 자동으로 MySql에 테이블이 생성됨
public class User {
	
	@Id // pk 라는걸 알려줌
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 해당 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	//만약 오라클을 연결하면 시퀀스를 사용한다는거, mysql이면 auto increase 사용한다는거
	private int id; // 시퀀스
	
	@Column(nullable = false,length = 30) // null 불가 길이 30
	private String username; // 아이디
	
	@Column(nullable = false,length = 100) // null 불가 길이 100
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 50) // null 불가 길이 50
	private String email; // 이메일
	
	@ColumnDefault("'user'") // 쌍따옴표 안에 홑따옴표 같이 넣어서 이게 문자라는걸 알려줘야함
	private String role; // Enum을 쓰는게 좋긴 함. 도메인을 만들때 좋음 이사람의 role은 admin, 일반유저, 매니저 뭐 이런 식으로 열거 
	// admin일땐 모든사람들 글 삭제, 일반회원은 자기것만, 매니저면 관리자페이지에서 홈페이지 관리
	// 도메인이라는건 프로그래밍에서 어떤 범위를 뜻함. 성별이란게 있다면 도메인은 남녀, 학년이란게 있으면 초등학생이면 1~6, 고등학생이면 1~3 이런식으로
	
	
	@CreationTimestamp // 시간이 자동으로 현재시간으로 입력됨
	private Timestamp createDate; // 가입 시간
	
	
}
