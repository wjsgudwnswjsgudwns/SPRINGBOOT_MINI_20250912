package com.jhj.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	public List<Room> findByState(String state);
	
	public  List<Room> findByRoomnameIn(List<String> roomNames);
}
