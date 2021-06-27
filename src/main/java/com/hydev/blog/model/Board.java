package com.hydev.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false,length=100)
	private String title; //제목
	
	@Lob // 대용량데이터 할때 사용
	private String content; //내용 섬머노트 라이브러리(일반적인 글이 디자인되어 들어감 <html> 코드가 섞여서 들어감
	
	@ColumnDefault("0")
	private int count; // 조회수
	
	@ManyToOne // board가 many, user가 one 한명의 유저는 여러개의 게시글을 쓸 수 있음 여러개의 게시글은 한명의 유저에 의해 쓰일 수 있음
	@JoinColumn(name="userid")
	private User user; // DB는 오브젝트를 저장할 수 없음. orm에선 외래키로 찾는게 아니라 user 오브젝트를 바로 넣으면 됨 이게 실제로 foreign key가 되는거
	
	@CreationTimestamp
	private Timestamp createDate; // 현재 시간
}
