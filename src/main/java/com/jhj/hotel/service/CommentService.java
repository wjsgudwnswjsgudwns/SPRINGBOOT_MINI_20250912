package com.jhj.hotel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhj.hotel.entity.Comment;
import com.jhj.hotel.entity.Freeboard;
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public Comment create(Freeboard freeboard, String content, HotelUser author) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setFreeboard(freeboard);
		comment.setAuthor(author);
		
		commentRepository.save(comment);
		
		return comment;
	}
	
	public Comment getComment(Integer id) {
		Optional<Comment> commentOptional = commentRepository.findById(id);
		
		if(commentOptional.isPresent()) {
			Comment comment = commentOptional.get();
			
			return comment;
		} else {
			throw new com.jhj.hotel.DataNotFoundException("존재하지 않는 댓글입니다.");
		}
		
	}
	
	public void modify(Comment comment, String content) {
		comment.setContent(content);
		
		commentRepository.save(comment);
		
	}
	
	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}
}
