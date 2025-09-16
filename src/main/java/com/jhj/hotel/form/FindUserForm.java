package com.jhj.hotel.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindUserForm {

	@NotEmpty(message = "아이디를 입력해주세요.")
	private String username;
	
	@NotEmpty(message = "이메일을 입력해주세요.")
	private String email;
	
	@NotEmpty(message = "이름을 입력해주세요.")
	private String userrealname;
	
	private Long id;
}
