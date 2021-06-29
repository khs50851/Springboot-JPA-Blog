package com.hydev.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydev.blog.model.User;

// jsp로 치면 DAO
// 스프링 레거시로 치면 자동으로 bean 등록이 됨 @Repository 어노테이션 생략 가능

public interface UserRepository extends JpaRepository<User, Integer>{ // User 테이블을 관리하는 레포지토리, 옆에는 유저 테이블의 pk 타입을 적음
	// jpa 레포지토리는 findall이라는 함수를 들고 있는데 이건 user테이블이 들고있는 모든 행을 리턴

}
