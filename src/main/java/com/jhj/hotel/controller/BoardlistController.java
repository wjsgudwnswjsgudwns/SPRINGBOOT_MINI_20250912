package com.jhj.hotel.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.form.BoardForm;
import com.jhj.hotel.service.BoardlistService;
import com.jhj.hotel.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/freeboard") 
public class BoardlistController {

	@Autowired
	private BoardlistService boardlistService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/boardlist")
	public String list() {
		
		return "boardlist";
	}
	
	@GetMapping(value = "/create")
	public String boardCreate(BoardForm boardForm) {
			
		return "boardwrite_form"; // 질문 등록하는 폼 페이지
	}
		
	@PostMapping(value = "/create")
	public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) { // 참이면 에러 발생 -> 유효성 체크
			return "boardwrite_form"; // 에러 발생시 다시 질문 등록 폼으로 이동
		}
		
		HotelUser hotelUser = userService.getUser(principal.getName());
		
		boardlistService.create(boardForm.getSubject(), boardForm.getContent(), hotelUser);
		
		return "redirect:/freeboard/boardlist";
		
	}
	
	@GetMapping(value = "/boardview/{id}")
	public String boardview(Model model, @PathVariable("id") Integer id) {
		
		boardlistService.hit(boardlistService.get)
		
		return new String();
	}
	
}
