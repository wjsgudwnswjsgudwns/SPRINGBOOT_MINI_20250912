package com.jhj.hotel.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.jhj.hotel.entity.Comment;
import com.jhj.hotel.entity.Freeboard;
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.form.CommentForm;
import com.jhj.hotel.service.BoardlistService;
import com.jhj.hotel.service.CommentService;
import com.jhj.hotel.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardlistService boardlistService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create/{id}")
	public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
		Freeboard freeboard = boardlistService.getBoard(id);
		
		HotelUser hotelUser = userService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("freeboard", freeboard);
			return "boardview";
		}
		
		Comment comment = commentService.create(freeboard, commentForm.getContent(), hotelUser);
		
		return String.format("redirect:/freeboard/boardview/%s#comment_%s", id, comment.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/modify/{id}")
    public String commentModify(@PathVariable("id") Integer id, Principal principal, CommentForm commentForm) {
		Comment comment = commentService.getComment(id);
		
		//글쓴 유저와 로그인한 유저의 동일 여부 검증
		if(!comment.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정 권한이 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		commentForm.setContent(comment.getContent());
		
		return "comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/modify/{id}")
	public String commentModify(@PathVariable("id") Integer id, Principal principal, @Valid CommentForm commentForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "comment_form";
		}
		
		Comment comment = commentService.getComment(id);
		
		//글쓴 유저와 로그인한 유저의 동일 여부 검증
		if(!comment.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정 권한이 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		commentService.modify(comment, commentForm.getContent());
		
		return String.format("redirect:/freeboard/boardview/%s", comment.getFreeboard().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer id, Principal principal) {
		Comment comment = commentService.getComment(id);
		
		commentService.delete(comment);
		
		return String.format("redirect:/freeboard/boardview/%s", comment.getFreeboard().getId());
		
	}
	 
}
