package com.jhj.hotel.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.form.ChangePasswordForm;
import com.jhj.hotel.form.FindUserForm;
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
	
	@PostMapping(value = "/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "signup";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return "signup";
		}
		
		try {
			userService.create(userCreateForm.getUserid(), userCreateForm.getPassword1(), userCreateForm.getEmail(),userCreateForm.getPhone(),userCreateForm.getUserrealname());
			} catch(DataIntegrityViolationException e) { // 중복된 데이터에 대한 예외 처리
				e.printStackTrace();
				
				// 이미 등록된 사용자의 아이디일 경우 발생하는 에러
				bindingResult.reject("signupFailed", "사용중인 아이디입니다.");
				return "signup";
				
			} catch(Exception e) { // 기타 나머지 예외 처리
				e.printStackTrace();
				bindingResult.reject("signupFailed", "회원 가입 실패.");
				return "signup";
			}
		
		
		return "redirect:/user/login";
	}
	
	@GetMapping(value = "/login")
	public String login() { // 로그인 화면 보이기
		
		return "login";
	}
	
	@GetMapping(value = "/findpassword")
	public String findpassword(Model model, FindUserForm findUserForm) {
		
		model.addAttribute("findUserForm", findUserForm);
		return "findpassword";
	}
	
	@PostMapping(value = "/resetpassword")
	public String findpassword(@Valid FindUserForm findUserForm, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("findUserForm", findUserForm);
			return "findpassword";
		}
		
		HotelUser user = userService.findUser(findUserForm.getUsername(), findUserForm.getUserrealname(), findUserForm.getEmail());
		model.addAttribute("user", user);
		
		ChangePasswordForm passwordForm = new ChangePasswordForm();
	    passwordForm.setUsername(user.getUsername());
	    passwordForm.setId(user.getId());
	    model.addAttribute("passwordChangeForm", passwordForm);
		return "change_password";

	}
	
	@PostMapping(value = "/update_password")
	public String update_password(@Valid @ModelAttribute("passwordChangeForm") ChangePasswordForm changePasswordForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "change_password";
		}
		
		  if (!changePasswordForm.getPassword1().equals(changePasswordForm.getPassword2())) {
		        bindingResult.rejectValue("password2", "mismatch", "비밀번호와 확인이 일치하지 않습니다.");
		        return "change_password";
		  }
		  
		  HotelUser user = userService.getUser(changePasswordForm.getUsername());
		  userService.updatePw(user, changePasswordForm.getPassword1());
		  
		  return "redirect:/user/login";
	}
	
}
