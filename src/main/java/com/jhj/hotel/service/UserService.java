package com.jhj.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 회원 가입
	public HotelUser create(String userid, String password, String email, String phone) {
		HotelUser user = new HotelUser();
		
		user.setUserid(userid);
		user.setEmail(email);
		user.setPhone(phone);
		
		String cryptPassword = passwordEncoder.encode(password);
		user.setPassword(cryptPassword);
		
		userRepository.save(user);
		
		return user;
	}
}
