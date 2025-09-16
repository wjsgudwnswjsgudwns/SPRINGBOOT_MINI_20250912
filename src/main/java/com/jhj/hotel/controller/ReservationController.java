package com.jhj.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhj.hotel.entity.HotelUser;
import com.jhj.hotel.entity.Reservation;
import com.jhj.hotel.entity.Room;
import com.jhj.hotel.service.ReservationService;
import com.jhj.hotel.service.RoomService;

@Controller
@RequestMapping(value = "/hotel")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping(value = "/reservation")
	public String reservation () {
		
		return "reservation_step1";
	}
	
	@GetMapping(value = "/step2")
	public String step2(Model model, @RequestParam("sdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sdate,
            @RequestParam("edate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate edate,
            @RequestParam("people") int people) {
		List<Room> availableRooms = roomService.getAvailableRooms();
		
		model.addAttribute("sdate", sdate);
        model.addAttribute("edate", edate);
        model.addAttribute("people", people);
        model.addAttribute("rooms", availableRooms);

        return "reservation_step2";
	}
	
	// Step2 → 예약 확정
    @PostMapping(value = "/confirm")
    public String confirm(@RequestParam("roomIds") List<Long> roomIds,
                          @RequestParam("sdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sdate,
                          @RequestParam("edate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate edate,
                          @RequestParam("people") Integer people,
                          @AuthenticationPrincipal HotelUser user, Model model) {

        Reservation reservation = reservationService.createReservation(sdate, edate, people, user, roomIds);
        
        model.addAttribute("reservation", reservation);
        model.addAttribute("selectedRooms", reservation.getRooms());
        
        return "reservation_confirm";
    }
    
    @GetMapping(value = "/complete")
    public String complete() {
    	
    	
    	return "myreservation";
    }
    
    @PostMapping(value = "/complete")
    public String complete(@RequestParam("roomNames") List<String> roomNames,
                                     @RequestParam("sdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sdate,
                                     @RequestParam("edate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate edate,
                                     @RequestParam("people") Integer people,
                                     Model model) {

        List<Room> selectedRooms = roomService.getRoomsByNames(roomNames);

        model.addAttribute("sdate", sdate);
        model.addAttribute("edate", edate);
        model.addAttribute("people", people);
        model.addAttribute("selectedRooms", selectedRooms);

        return "/";
    }
	
	
	
}
