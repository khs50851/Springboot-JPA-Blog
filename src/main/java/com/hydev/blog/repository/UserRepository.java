package com.hydev.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hydev.blog.model.User;

// jsp로 치면 DAO
// 스프링 레거시로 치면 자동으로 bean 등록이 됨 @Repository 어노테이션 생략 가능

public interface UserRepository extends JpaRepository<User, Integer>{ // User 테이블을 관리하는 레포지토리, 옆에는 유저 테이블의 pk 타입을 적음
	
	// select * from user where username=?; By 뒤에는 where절에 나오는건데 첫글자를 대문자로 써야함
	Optional<User> findByUsername(String username);
	
	
	
	// jpa 레포지토리는 findall이라는 함수를 들고 있는데 이건 user테이블이 들고있는 모든 행을 리턴

	/*
	 2021-07-05 전통적인 로그인 방식의 쿼리들
	 
	User findByUsernameAndPassword(String username,String password); // JPA Naming 쿼리 이 함수는 없는데 이렇게 함수를 만들면
	 select * from user where username =?첫번째 파라미터 and password = ?두번째 파라미터 이 쿼리가 날라감 각각의 ? 에는 파라미터들이 들어가게됨
	
	이렇게 하면 userReposity.login하면 이 쿼리가 날라감, 복잡한 쿼리 할때 이 방식도 이용 가능
	@Query(value="select * from user where username=? and password = ?",nativeQuery = true)
	User login(String username,String password);
	*/
}
