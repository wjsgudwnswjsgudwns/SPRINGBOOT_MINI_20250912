package com.jhj.hotel.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "freeboard") // 실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "FREEBOARD_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "FREEBOARD_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Freeboard {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREEBOARD_SEQ_GENERATOR")
	private Integer id; // 질문 게시판의 글번호 (기본키, 자동 번호 증가)
	
	@Column(length = 200)
	private String subject; // 질문게시판 제목
	
	@Column(length = 2000) // 
	private String content; // 질문게시판 내용
	
	@CreationTimestamp
	private LocalDateTime createdate; // 질문게시판 등록일
	
	private Integer hit=0;
	
	@ManyToOne
	private HotelUser author;
	
	@OneToMany(mappedBy = "freeboard", cascade = CascadeType.REMOVE)
	private List<Comment> commentlist;
	
	@ManyToMany
	Set<HotelUser> like;
	
	@ManyToMany
	Set<HotelUser> dislike;
}
