package com.jhj.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
