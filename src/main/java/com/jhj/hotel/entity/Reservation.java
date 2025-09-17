package com.jhj.hotel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "reservation") // 실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "RESERVATION_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "RESERVATION_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQ_GENERATOR")
	private Integer id; // 질문 게시판의 글번호 (기본키, 자동 번호 증가)

	private LocalDate sdate; // 예약 날짜
	
	private LocalDate edate; // 끝나는 날짜

	private Integer people; // 투숙 인원 수
	
	@CreationTimestamp
	private LocalDateTime rdate; // 예약 날짜
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private HotelUser author; // 예약한 사람

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
	private List<Room> rooms; // 예약에 잡힌 방 정보

	public void addRoom(Room room) {
        if (room == null) return;
        if (rooms == null) rooms = new ArrayList<>();
        if (!rooms.contains(room)) {
            rooms.add(room);
        }
        room.setReservation(this);
    } 
	
}
