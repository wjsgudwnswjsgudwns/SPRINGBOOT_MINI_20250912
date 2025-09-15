package com.jhj.hotel.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "commenttbl") // 실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "COMMENT_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "COMMENT_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Comment {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQ_GENERATOR")
	private Integer id; // 기본키, 자동 증가
	
	@Column(length = 2000)
	private String content; // 답변 게시판 내용
	
	@CreationTimestamp
	private LocalDateTime createdate;
	
	@ManyToOne
	private Freeboard freeboard;
	
	@ManyToOne
	private HotelUser author;
	
	@ManyToOne
	private Noticeboard noticeboard;
	
}
