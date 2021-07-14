package com.hydev.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hydev.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	@Modifying
	@Query(value="INSERT INTO reply(userid,boardid,content,createDate) VALUES(?1,?2,?3,now())",nativeQuery = true) // DTO 변수들이 차례로 들어감
	public int mSave(int userid,int boardid,String content); // jdbc가 수행되면 업데이트 된 행위 개수를 리턴해줌 (1이면 1개 업데이트)
}
