package com.jhj.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.hotel.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
