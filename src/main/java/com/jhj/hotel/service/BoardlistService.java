package com.jhj.hotel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhj.hotel.entity.Freeboard;
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.repository.BoardlistRepository;

@Service
public class BoardlistService {

	@Autowired
	private BoardlistRepository boardlistRepository;
	
	
	public void create(String subject, String content, HotelUser user) {
		Freeboard freeboard = new Freeboard();
		freeboard.setSubject(subject);
		freeboard.setContent(content);
		freeboard.setAuthor(user);
		boardlistRepository.save(freeboard);
	}
	
	public void hit(Freeboard freeboard) {
		freeboard.setHit(freeboard.getHit()+1);
		boardlistRepository.save(freeboard);
	}
	
	public Freeboard getBoard(Integer id) {
		Optional<Freeboard> freeboard = boardlistRepository.findById(id);
		
	}
}
