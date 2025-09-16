package com.jhj.hotel.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateForm {

	@NotEmpty(message = "아이디를 입력해주세요.")
	private String userid;
	
	@NotEmpty(message = "비밀번호를 입력해주세요.")
	@Size(min=8, max = 16, message = "비밀번호는 8~16사이여야 합니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인을 입력해주세요.")
	@Size(min=8, max = 16, message = "비밀번호는 8~16사이여야 합니다.")
	private String password2;
	
	@NotEmpty(message = "이메일을 입력해주세요.")
	private String email;
	
	@NotEmpty(message = "이름을 입력해주세요.")
	private String userrealname;
	
	@NotEmpty(message = "핸드폰 번호를 입력해주세요.")
	private String phone;
}
