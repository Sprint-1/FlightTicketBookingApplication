package com.capg.ftb.controller;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.ftb.model.Booking;
import com.capg.ftb.model.ScheduledFlight;
import com.capg.ftb.service.BookingServiceImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/ftb")
@Api
@CrossOrigin
public class BookingController {
	
	private static final Logger log =LogManager.getLogger(BookingController.class);
	
	@Autowired
	private BookingServiceImpl bookingService;
	
	@PostMapping(value="/addBooking")
	public ResponseEntity<Booking> addBooking( @RequestBody Booking booking)
	{
		Booking booking1=bookingService.addBooking(booking);
		log.info("Booking bas been made");
		return new ResponseEntity<Booking>(booking1,HttpStatus.CREATED);
	}
	
	
	
	@PutMapping(value="/modifyBooking/{bookingId}")
	public ResponseEntity<Booking> modifyBooking(@Valid @RequestBody Booking booking,@PathVariable BigInteger bookingId) 
	{
		Booking booking1= bookingService.modifyBooking(booking, bookingId);
		
		log.info("Modified a booking");
		return  new ResponseEntity<Booking>(booking1,HttpStatus.OK);
	}
	
	@GetMapping("/viewBooking/{id}")
	public ResponseEntity<Booking> searchBookingByID(@PathVariable("id") BigInteger bookingId) {

		Booking booking1=bookingService.viewBooking(bookingId);
		log.info("Viewed the booking details");
		return new ResponseEntity<Booking>(booking1,HttpStatus.OK);
	}
	
	@GetMapping("/viewAllBookings")
	public ResponseEntity<List<Booking>> readAllBookings()
	{
		List<Booking> allBookings=bookingService.viewAllBookings();
		log.info("Viewed booking made by now ");
		return new ResponseEntity<List<Booking>>(allBookings,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBooking/{id}")
	public ResponseEntity<Booking> deleteBookings(@PathVariable("id") BigInteger bookingId) {

		Booking booking1=bookingService.deleteBooking(bookingId);
		log.info("Deleted a booking");
		return new ResponseEntity<Booking>(booking1,HttpStatus.OK);
	}
	
//	@GetMapping(value="/validateBooking")
//	public ResponseEntity<String> validateBooking(@Valid @RequestBody BigInteger booking)
//	{
//	 
//       Booking booking1=bookingService.validateBooking(booking);
//		if(booking1!=null)
//		{
//		return new ResponseEntity<String>("Booking ID: "+booking1.getBookingId()+"\nUser ID: "+booking1.getUserId()+"\nYour Booking is confirmed. Happy Journey!",HttpStatus.OK);
//		}
//		else
//		{
//			return new ResponseEntity<String>(" Booking Not Found! ",HttpStatus.OK);		
//		}
//	}
		
	@GetMapping(value="/searchForScheduledFlight/{srcAirport}/{dstnAirport}/{deptDate}")
	public ResponseEntity<List<ScheduledFlight>> searchForScheduledFlight(@PathVariable String srcAirport,@PathVariable String dstnAirport,@PathVariable String deptDate)
	{
		List<ScheduledFlight> sFlights=bookingService.searchForScheduledFlight(srcAirport, dstnAirport, deptDate);
		return new ResponseEntity<List<ScheduledFlight>>(sFlights,HttpStatus.OK);
	}

	
	@GetMapping("/getBookingsByUserName/{userName}")
	public ResponseEntity<List<Booking>> getBookingsByUserName(@PathVariable("userName") String userName) {

		List<Booking> allBookings=bookingService.getByUserName(userName);
		log.info("Deleted a booking");
		return new ResponseEntity<List<Booking>>(allBookings,HttpStatus.OK);
	}

}
