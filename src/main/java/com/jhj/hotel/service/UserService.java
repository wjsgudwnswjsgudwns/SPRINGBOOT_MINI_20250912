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
	public HotelUser create(String username, String password, String email, String phone, String userrealname) {
		HotelUser user = new HotelUser();
		
		user.setUsername(username);
		user.setEmail(email);
		user.setPhone(phone);
		user.setUserrealname(userrealname);
		
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
	
	public HotelUser findUser(String username, String userrealname, String email) {
		HotelUser finduser = userRepository.findByUsernameAndUserrealnameAndEmail(username, userrealname, email);
		
		if(finduser != null) {
			return finduser;
		} else {
			throw new DataNotFoundException("존재하지 않는 유저입니다");
		}

	}
	
	public HotelUser updatePw(HotelUser user, String password) {
		
		String cryptPassword = passwordEncoder.encode(password);
		user.setPassword(cryptPassword);
		
		userRepository.save(user);
		
		return user;
	}
	
	
}
