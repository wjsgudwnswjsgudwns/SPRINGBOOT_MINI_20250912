package com.jhj.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
		name = "ROOM_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "ROOM_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOM_SEQ_GENERATOR")
	private Long id;
	
	@Column(unique = true)
	private String roomname; // 방 이름
	
	private String state; // 방 예약 상태
	
	@ManyToOne
	private Reservation reservation; // 방에 잡힌 예약 정보
}
