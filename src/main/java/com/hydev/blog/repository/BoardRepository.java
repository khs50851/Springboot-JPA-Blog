package com.hydev.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hydev.blog.model.Board;
import com.hydev.blog.model.User;


public interface BoardRepository extends JpaRepository<Board, Integer>{ // User 테이블을 관리하는 레포지토리, 옆에는 유저 테이블의 pk 타입을 적음
	
}
