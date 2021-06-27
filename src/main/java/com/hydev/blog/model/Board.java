package com.hydev.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	@ManyToOne(fetch = FetchType.EAGER) // board가 many, user가 one 한명의 유저는 여러개의 게시글을 쓸 수 있음 여러개의 게시글은 한명의 유저에 의해 쓰일 수 있음
	// fettype eager user는 하나밖에 없으므로 하나 가져오겠다
	@JoinColumn(name="userid")
	private User user; // DB는 오브젝트를 저장할 수 없음. orm에선 외래키로 찾는게 아니라 user 오브젝트를 바로 넣으면 됨 이게 실제로 foreign key가 되는거
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) //Reply 클래스에 있는 board 객체 넣음 (mappedBy가 적혀 있으면 연관관계의 주인이 아니다(한마디로 난 FK가 아님) DB에 컬럼을 만들지 마세요.) 여기선 Reply 테이블의 board가 FK임 
	// 이건 그냥 board를 셀렉트할때 조인문을 통해 값을 얻기 위함임 OneToMany 기본은 FetchType.LAZY이지만 여기선 바로 댓글을 가져와야하므로 EAGER로 바꿈
	// mappedBy 뒤에 나오는건 필드 이름임
	
	// 이건 JoinColmn 필요없음, 실제 mysql 테이블에 댓글 외래키가 필요없음
	// 만약 이게 만들어진다면 게시글 한 레코드에 댓글은 많이 달려있을 수 있는데 replyid에 1,2,3,4 이런식으로 데이터가 들어갈 순 없음
	private List<Reply> reply; // 한 게시글의 댓글은 여러개이므로 list로 들어가야함
	
	@CreationTimestamp
	private Timestamp createDate; // 현재 시간
}
