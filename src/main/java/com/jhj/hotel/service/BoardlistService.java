package com.jhj.hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jhj.hotel.DataNotFoundException;
import com.jhj.hotel.entity.Freeboard;
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.repository.BoardlistRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
		
		if(freeboard.isPresent()) {
			return freeboard.get();
		} else {
			throw new DataNotFoundException("존재하지 않는 게시물입니다.");
		}
		
	}
	
	public void modify(Freeboard freeboard, String subject, String content) {
		freeboard.setSubject(subject);
		freeboard.setContent(content);
		
		boardlistRepository.save(freeboard);
	}
	
	public void delete(Freeboard freeboard) {
		boardlistRepository.delete(freeboard);
	}
	
	public Page<Freeboard> getPageFreeboard(int page, String kw){
		int pageSize =  10; // 페이지 당 10개씩 글 출력
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
		Specification<Freeboard> spec = search(kw);
		
		return boardlistRepository.findAll(spec, pageable);
		
	}

	private Specification<Freeboard> search(String kw) { // 키워드 (kw) 로 조회
		return new Specification<>() {
			
			private static final long serialVersionUID = 1L; // 변조 방지
			
			@Override
			public Predicate toPredicate(Root<Freeboard> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복 제거
				Join<Freeboard, HotelUser> u1 = q.join("author", JoinType.LEFT);
				return 	cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%" + kw + "%"), // 내용
						cb.like(u1.get("username"), "%" + kw + "%")); // 질문 작성자
				
			}
		};
	}
}
