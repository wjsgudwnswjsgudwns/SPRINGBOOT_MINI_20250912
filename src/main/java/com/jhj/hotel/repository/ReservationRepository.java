package com.jhj.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.Reservation;
import java.util.List;
import com.jhj.hotel.entity.HotelUser;


public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	public List<Reservation> findByAuthor(HotelUser author);
}
