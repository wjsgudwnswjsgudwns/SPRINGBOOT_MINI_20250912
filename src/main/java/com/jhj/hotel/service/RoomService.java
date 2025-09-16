package com.jhj.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhj.hotel.entity.Room;
import com.jhj.hotel.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	public List<Room> getAvailableRooms() { // 예약 가능한 방 조회
		List<Room> rooms = roomRepository.findByState("AVAILABLE");
		return rooms;
	}
	
	public List<Room> getRoomsByIds(List<Long> roomIds) { // ID로 방 조회
		return roomRepository.findAllById(roomIds);
	}
	
	public void updateRoomsToReserved(List<Room> rooms) { // 방 상태 업데이트
        for (Room room : rooms) {
            room.setState("RESERVED");
        }
        roomRepository.saveAll(rooms);
    }
	
	 public List<Room> getRoomsByNames(List<String> roomNames) {
	        return roomRepository.findByRoomnameIn(roomNames);
	 }
	 
	 public void updateRoom(Room room) {
	        roomRepository.save(room); 
	    }
}
