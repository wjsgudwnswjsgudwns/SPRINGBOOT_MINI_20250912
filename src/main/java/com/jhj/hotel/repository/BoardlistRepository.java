package com.jhj.hotel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.Freeboard;

public interface BoardlistRepository extends JpaRepository<Freeboard,Integer>{

	public Page<Freeboard> findAll(Pageable pageable);
	
	public Page<Freeboard> findAll(Specification<Freeboard> spec,Pageable pageable);
	
}
