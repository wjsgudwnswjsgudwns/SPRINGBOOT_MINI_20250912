package com.jhj.hotel.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "noticetbl")
@SequenceGenerator(
		name = "NOTICE_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "NOTICE_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Noticeboard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_SEQ_GENERATOR")
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(length = 2000)
	private String content;
	
	@CreationTimestamp
	private LocalDateTime createdate; // 질문게시판 등록일
	
	private Integer hit=0;
	
	@ManyToOne
	private HotelUser author;
	
	@OneToMany(mappedBy = "noticeboard", cascade = CascadeType.REMOVE)
	private List<Comment> commentlist;
}
