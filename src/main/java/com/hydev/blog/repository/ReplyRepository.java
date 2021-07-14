package com.hydev.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hydev.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

}
