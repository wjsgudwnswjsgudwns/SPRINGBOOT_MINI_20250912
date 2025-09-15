package com.jhj.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jhj.hotel.entity.Freeboard;
import com.jhj.hotel.entity.HotelUser;

@Service
public class UserSecurityService implements UserDetailsService {
	
	@Autowired
	private com.jhj.hotel.repository.UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<HotelUser> _siteUser = userRepository.findByUsername(username);
		
		if(_siteUser.isEmpty()) { // 해당 아이디 레코드 없음
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		HotelUser siteUser = _siteUser.get(); // 아이디로 찾은 레코드
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		// 사용자 권한 정보를 나타내는 GrantedAuthority 객체들의 리스트
		
		if("admin".equals(username)) { // 참이면 admin 권한
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else { // 그렇지 않으면 user
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities); 
		// 아이디랑 패스워드랑 권한을 넣어서 User라는 객체를 만들어서 보내줌
		// 비밀번호가 일치하는지 비교까지 해줌
	}
	

}
