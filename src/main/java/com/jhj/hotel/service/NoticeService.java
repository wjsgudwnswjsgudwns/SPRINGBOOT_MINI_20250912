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
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.entity.Noticeboard;
import com.jhj.hotel.repository.NoticeRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	public Page<Noticeboard> getNoticeboard(int page, String kw){
		int pageSize = 10;
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
		Specification<Noticeboard> spec = search(kw);
		
		return noticeRepository.findAll(spec, pageable);
	}
	
	private Specification<Noticeboard> search(String kw) { // 키워드 (kw) 로 조회
		return new Specification<>() {
			
			private static final long serialVersionUID = 1L; // 변조 방지
			
			@Override
			public Predicate toPredicate(Root<Noticeboard> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복 제거
				Join<Noticeboard, HotelUser> u1 = q.join("author", JoinType.LEFT);
				return 	cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(u1.get("username"), "%" + kw + "%")); // 질문 작성자
				
			}
		};
	}
	
	
	public void create(String subject, String content, HotelUser user) {
		Noticeboard notice = new Noticeboard();
		notice.setSubject(subject);
		notice.setContent(content);
		notice.setAuthor(user);
		
		noticeRepository.save(notice);
		
	}
	
	public Noticeboard getNotice(Integer id) {
		Optional<Noticeboard> oNotice = noticeRepository.findById(id);
		
		if(oNotice.isPresent()) {
			Noticeboard notice = oNotice.get();
			return notice;
		} else {
			throw new DataNotFoundException("존재하지 않는 게시물입니다.");
		}
	}
	
	public void hit (Noticeboard notice) {
		notice.setHit(notice.getHit()+1);
		noticeRepository.save(notice);
	}
	
	public void modify (Noticeboard noticeboard, String subject, String content) {
		noticeboard.setSubject(subject);
		noticeboard.setContent(content);
		
		noticeRepository.save(noticeboard);
	}
	
}
