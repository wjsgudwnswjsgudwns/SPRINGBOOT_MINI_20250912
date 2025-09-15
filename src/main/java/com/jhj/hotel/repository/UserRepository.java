package com.jhj.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.HotelUser;

public interface UserRepository extends JpaRepository<HotelUser, Long> {
	
	public Optional<HotelUser> findByUsername(String username); // 아이디로 계정 찾기

}
