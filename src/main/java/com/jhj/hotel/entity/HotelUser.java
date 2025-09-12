package com.jhj.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "hoteluser")
@SequenceGenerator(
		name = "USER_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "USERJOIN_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class HotelUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
	private Long id; // 유저 번호, 기본키
	
	@Column(unique = true)
	private String userid; // 아이디
	
	private String password; // 비밀번호
	
	@Column(unique = true)
	private String email; // 이메일
	
	@Column(unique = true)
	private String phone; // 핸드폰
	
}
