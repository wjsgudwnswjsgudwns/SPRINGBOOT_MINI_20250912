package com.jhj.hotel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.Noticeboard;

public interface NoticeRepository extends JpaRepository<Noticeboard, Integer> {
	
	public Page<Noticeboard> findAll(Pageable pageable);
	
	public Page<Noticeboard> findAll(Specification<Noticeboard> spec, Pageable pageable);
}
