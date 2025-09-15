package com.jhj.hotel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jhj.hotel.DataNotFoundException;
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 회원 가입
	public HotelUser create(String username, String password, String email, String phone) {
		HotelUser user = new HotelUser();
		
		user.setUsername(username);
		user.setEmail(email);
		user.setPhone(phone);
		
		String cryptPassword = passwordEncoder.encode(password);
		user.setPassword(cryptPassword);
		
		userRepository.save(user);
		
		return user;
	}
	
	public HotelUser getUser(String username) {
		Optional<HotelUser> getuser = userRepository.findByUsername(username);
		if(getuser.isPresent()) {
			HotelUser user = getuser.get();
			return user;
		} else {
			throw new DataNotFoundException("존재하지 않는 유저입니다");
		}
	}
	
}
