package com.jhj.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.HotelUser;

public interface UserRepository extends JpaRepository<HotelUser, Long> {

}
