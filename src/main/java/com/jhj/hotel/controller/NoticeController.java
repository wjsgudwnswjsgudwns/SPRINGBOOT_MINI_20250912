package com.jhj.hotel.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.entity.Noticeboard;
import com.jhj.hotel.form.CommentForm;
import com.jhj.hotel.form.NoticeForm;
import com.jhj.hotel.service.NoticeService;
import com.jhj.hotel.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/noticelist")
	public String noticelist(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Noticeboard> paging = noticeService.getNoticeboard(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		
		return "noticelist";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/noticeview/{id}")
	public String noticeview(@PathVariable("id") Integer id, Model model, CommentForm commentForm) {
		
		noticeService.hit(noticeService.getNotice(id));
		Noticeboard notice = noticeService.getNotice(id);
		model.addAttribute("notice", notice);
		
		return "noticeview";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/create")
	public String create(@ModelAttribute("noticeform") NoticeForm noticeform) {

		return "noticewrite";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create")
	public String create(@Valid NoticeForm noticeform, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "noticewrite";
		}
		
		HotelUser user = userService.getUser(principal.getName());
		
		noticeService.create(noticeform.getSubject(), noticeform.getContent(), user);
		
		return "redirect:/notice/noticelist";
	}

	
	
}
