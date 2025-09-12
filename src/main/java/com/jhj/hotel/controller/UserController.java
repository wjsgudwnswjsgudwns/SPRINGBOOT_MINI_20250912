package com.jhj.hotel.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhj.hotel.form.UserCreateForm;
import com.jhj.hotel.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }
	
	@GetMapping(value = "/signup")
	public String signup(UserCreateForm userCreateForm) {
		
		return "signup";
	}
	
	@PostMapping(value = "signup")
	public String signup(BindingResult bindingResult,@Valid UserCreateForm userCreateForm) {
		
		if(bindingResult.hasErrors()) {
			return "signup";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return "signup";
		}
		
		try {
			userService.create(userCreateForm.getUserid(), userCreateForm.getPassword1(), userCreateForm.getEmail(),userCreateForm.getPhone());
			} catch(DataIntegrityViolationException e) { // 중복된 데이터에 대한 예외 처리
				e.printStackTrace();
				
				// 이미 들옥된 사용자의 아이디일 경우 발생하는 에러
				bindingResult.reject("signupFailed", "사용중인 아이디입니다.");
				return "signup_form";
				
			} catch(Exception e) { // 기타 나머지 예외 처리
				e.printStackTrace();
				bindingResult.reject("signupFailed", "회원 가입 실패.");
				return "signup_form";
			}
		
		
		return "/";
	}
	
	@GetMapping(value = "/login")
	public String login(UserCreateForm userCreateForm) {
		
		return "login";
	}
}
