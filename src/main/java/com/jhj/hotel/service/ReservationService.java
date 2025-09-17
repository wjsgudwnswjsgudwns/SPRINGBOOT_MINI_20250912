package com.jhj.hotel.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhj.hotel.DataNotFoundException;
import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.entity.Reservation;
import com.jhj.hotel.entity.Room;
import com.jhj.hotel.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RoomService roomService;
	
	public Reservation createReservation(LocalDate sdate, LocalDate edate, Integer people, HotelUser user, List<Long> roomIds) {
	
		Reservation reservation = new Reservation();
        reservation.setSdate(sdate);
        reservation.setEdate(edate);
        reservation.setAuthor(user);
        reservation.setPeople(people);

        reservation = reservationRepository.save(reservation);
	
	     List<Room> rooms = roomService.getRoomsByIds(roomIds);
	     for (Room room : rooms) {
	         room.setReservation(reservation);
	         roomService.updateRoom(room); // save/update
	     }
	
	     roomService.updateRoomsToReserved(rooms);
	
	     return reservation;
	}
	
	public List<Reservation> myReservations(HotelUser user) {
		
		List<Reservation> reservations = reservationRepository.findByAuthor(user);
		
		return reservations;
		
	}
	
	public void delete(Reservation reservation) {
		reservationRepository.delete(reservation);
	}
	
	public Reservation getReservation(Integer id) {
		Optional<Reservation> oReservation = reservationRepository.findById(id);
		if(oReservation.isPresent()) {
			Reservation reservation  = oReservation.get();
			return reservation;
		} else {
			throw new DataNotFoundException("존재하지 않는 예약입니다.");
		}
	}
}
