package com.jhj.hotel.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm {

	
	@NotEmpty(message = "비밀번호를 입력해주세요.") 
	@Size(min=8, max = 16, message = "비밀번호는 8~16사이여야 합니다.") 
	private String password1; @NotEmpty(message = "비밀번호 확인을 입력해주세요.") 
	
	@Size(min=8, max = 16, message = "비밀번호는 8~16사이여야 합니다.") 
	private String password2;
	
	private String username;
	
	private Long id;
}
