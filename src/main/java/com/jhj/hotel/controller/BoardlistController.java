package com.jhj.hotel.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhj.hotel.entity.Freeboard;
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.form.BoardForm;
import com.jhj.hotel.form.CommentForm;
import com.jhj.hotel.service.BoardlistService;
import com.jhj.hotel.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequestMapping("/freeboard") 
public class BoardlistController {

	@Autowired
	private BoardlistService boardlistService;
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/boardlist")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw, @RequestParam(value = "type", defaultValue = "all") String type) {
		Page<Freeboard> paging = boardlistService.getPageFreeboard(page,kw, type);
		model.addAttribute("paging",paging);
		model.addAttribute("kw", kw);	
		model.addAttribute("type", type);
		return "boardlist";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/likelist")
	public String likelist(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw, @RequestParam(value = "type", defaultValue = "all") String type) {
		Page<Freeboard> paging = boardlistService.getPageLikeboard(page,kw, type);
		model.addAttribute("paging",paging);
		model.addAttribute("kw", kw);
		model.addAttribute("type", type);
		return "likelist";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/create")
	public String boardCreate(BoardForm boardForm) {
			
		return "boardwrite_form"; // 질문 등록하는 폼 페이지
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create")
	public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) { // 참이면 에러 발생 -> 유효성 체크
			return "boardwrite_form"; // 에러 발생시 다시 질문 등록 폼으로 이동
		}
		
		HotelUser hotelUser = userService.getUser(principal.getName());
		
		boardlistService.create(boardForm.getSubject(), boardForm.getContent(), hotelUser);
		
		return "redirect:/freeboard/boardlist";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/boardview/{id}")
	public String boardview(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
		
		boardlistService.hit(boardlistService.getBoard(id));
		
		Freeboard freeboard = boardlistService.getBoard(id);
		model.addAttribute("freeboard", freeboard);
		return "boardview";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/modify/{id}")
	public String boardModify(BoardForm boardForm, @PathVariable("id") Integer id, Principal principal) {
		Freeboard freeboard = boardlistService.getBoard(id);
		
		if(!freeboard.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		boardForm.setSubject(freeboard.getSubject());
		boardForm.setContent(freeboard.getContent());
		
		return "boardwrite_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/modify/{id}")
	public String boardModify(@Valid BoardForm boardForm, @PathVariable("id") Integer id, Principal principal, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "boardwrite_form";
		}
		Freeboard freeboard = boardlistService.getBoard(id); // 게시글 가져옴
		
		// 같은지 확인
		if(!freeboard.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		// 수정
		boardlistService.modify(freeboard, boardForm.getSubject(), boardForm.getContent());
		
		return String.format("redirect:/freeboard/boardview/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/delete/{id}")
	public String boardDelete(@PathVariable("id") Integer id, Principal principal) {
		Freeboard freeboard = boardlistService.getBoard(id);
		boardlistService.delete(freeboard);
		
		
		return "redirect:/freeboard/boardlist";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/like/{id}")
	public String like(@PathVariable("id") Integer id, Principal principal) {
		Freeboard freeboard = boardlistService.getBoard(id);
		HotelUser user = userService.getUser(principal.getName());
		//로그인한 유저의 아이디로 유저 엔티티 조회하기
		
		
		boardlistService.like(freeboard, user);
		return String.format("redirect:/freeboard/boardview/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/dislike/{id}")
	public String dislike(@PathVariable("id") Integer id, Principal principal) {
		Freeboard freeboard = boardlistService.getBoard(id);
		HotelUser user = userService.getUser(principal.getName());
		//로그인한 유저의 아이디로 유저 엔티티 조회하기
		
		
		boardlistService.dislike(freeboard, user);
		return String.format("redirect:/freeboard/boardview/%s", id);
	}
	
}
