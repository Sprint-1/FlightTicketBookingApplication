/*
 * Author : Thiracy Mary
 */

package com.capg.ftb.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.ftb.dao.IBookingDAO;
import com.capg.ftb.dao.IScheduleFlightDAO;
import com.capg.ftb.dao.IUsersDAO;
import com.capg.ftb.exception.AirportNotFoundException;
import com.capg.ftb.exception.BookingsExceptions;
import com.capg.ftb.exception.FlightNotFoundException;
import com.capg.ftb.exception.RecordNotFoundException;
import com.capg.ftb.exception.SeatsNotAvailableException;
import com.capg.ftb.exception.UserNotFoundException;
import com.capg.ftb.model.Booking;
import com.capg.ftb.model.Passenger;
import com.capg.ftb.model.Schedule;
import com.capg.ftb.model.ScheduledFlight;
import com.capg.ftb.model.Users;

@Service
public class BookingServiceImpl implements IFlightBookingService{

	@Autowired
	private IBookingDAO bookingDao;

	@Autowired
	private IScheduleFlightDAO scheduleFilghtDao;

	@Autowired 
	IUsersDAO usersDao;

	@Autowired
	public UsersServiceImpl userService;

	@Autowired
	private ScheduledFlightServiceImpl scheduledFService;


	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	Date date=new Date();
	
	@Override
	public Booking addBooking(Booking booking) {
		// TODO Auto-generated method stub


		Optional<ScheduledFlight> optional1=scheduleFilghtDao.findById(booking.getScheduledFlightId());
		ScheduledFlight scheduledFlight=(ScheduledFlight)optional1.orElseThrow(() ->new FlightNotFoundException("Scheduled Flight Not Found with the given ID : "+booking.getScheduledFlightId()));

		if(validateAttributes(booking) && validateUserName(booking.getUserName()))
		{


			if(booking.getNoOfPassangers()>scheduledFlight.getAvailableSeats())
			{
				throw new SeatsNotAvailableException("Passengers are more than the available seats");
			}
			else
			{
				LocalDate date = LocalDate.now();
				booking.setBookingDate(date);
                booking.setNoOfPassangers(1);
			
				booking.setTicketCost(scheduledFlight.getCostPerHead()*booking.getNoOfPassangers());

				modifyScheduledFlight(scheduledFlight.getScheduleFlightId(),booking.getNoOfPassangers());

				Booking booking1=bookingDao.save(booking);
				return booking1;
			}

		}
		else
		{
			return null;
		}


	}



	@Override
	public Booking modifyBooking(Booking booking,BigInteger bookingId)
	{
		Booking booking1=getById(bookingId);
//		Optional<ScheduledFlight> optional1=scheduleFilghtDao.findById(booking.getScheduledFlightId());
//		ScheduledFlight scheduledFlight=(ScheduledFlight)optional1.orElseThrow(() ->new FlightNotFoundException("Scheduled Flight Not Found with the given ID : "+booking1.getScheduledFlightId()));
//
//	
		if(booking1==null)
		{
			return null;
		}
		else
		{
			if(!(booking.getUserName().equals(booking1.getUserName())))
			{
				throw new BookingsExceptions("User Name cannot be modfied");
			}
			if(validateAttributes(booking))
			{
				
//				modifyScheduledFlightAdd(scheduledFlight.getScheduleFlightId(),booking1.getNoOfPassangers());
		//		bookingDao.deleteById(bookingId);
				Booking booking2=bookingDao.save(booking);
				return booking2;
			}
			else
			{
				return null;
			}
		}	
	}	


	@Override
	public Booking viewBooking(BigInteger bookingId) {
		// TODO Auto-generated method stub
		Optional<Booking> optional=bookingDao.findById(bookingId);
		Booking vbooking=optional.orElseThrow(()->new RecordNotFoundException("Booking Not Existed with the id : "+bookingId));
		return vbooking;
	}

	@Override
	public List<Booking> viewAllBookings() {
		// TODO Auto-generated method stub
		List<Booking> bookingList=(List<Booking>) bookingDao.findAll();
		return bookingList;
	}



	@Override
	public Booking deleteBooking(BigInteger bookingId) {
		// TODO Auto-generated method stub
		
		Booking booking2=getById(bookingId);

		Optional<ScheduledFlight> optional1=scheduleFilghtDao.findById(booking2.getScheduledFlightId());
		ScheduledFlight scheduledFlight=(ScheduledFlight)optional1.orElseThrow(() ->new FlightNotFoundException("Scheduled Flight Not Found with the given ID : "+booking2.getScheduledFlightId()));

		if(booking2==null)
		{
			return null;
		}
		else
		{

			bookingDao.deleteById(bookingId);
			modifyScheduledFlightAdd(scheduledFlight.getScheduleFlightId(),booking2.getNoOfPassangers());
			return booking2;
		}	
	}


	// validate UserName
	
	public boolean validateUserName(String userName)
	{
		Users user=usersDao.findByUserName(userName);
		if(user==null)
		{
			throw new UserNotFoundException("User with given Name not existed");
		}
		else
		{
		return true;
		}
	}

	@Override
	public Booking getById(BigInteger bookingId) {
		// TODO Auto-generated method stub
		Optional<Booking> optional=bookingDao.findById(bookingId);
		Booking booking1=optional.orElseThrow(()->new RecordNotFoundException("Booking ID does not exist : "+bookingId));
		return booking1;
	}

	@Override
	public List<ScheduledFlight> searchForScheduledFlight(String srcAirport, String dstnAirport, String deptDate) {
		// TODO Auto-generated method stub
		List<ScheduledFlight> list = scheduleFilghtDao.findAll();
		List<ScheduledFlight> list1=new ArrayList<ScheduledFlight>();
		for(int i=0;i<list.size();i++)
		{
			ScheduledFlight sFlight=list.get(i);
			System.out.println(sFlight.getSchedule().getSrcAirport()+" \n"+sFlight.getSchedule().getDstnAirport()+" "+sFlight.getSchedule().getDeptDate());
			if(sFlight.getSchedule().getSrcAirport().equals(srcAirport) && sFlight.getSchedule().getDstnAirport().equals(dstnAirport))
			{
				list1.add(sFlight);
			}
		}
		if(list1.size()==0)
		{
			throw new FlightNotFoundException("Invalid details or no scheduled flight found");
		}
		else
		{
		return list1;
		}
	}

	@Override
	public void modifyScheduledFlight(BigInteger scheduledFlightId,int passengersCount) {
		// TODO Auto-generated method stub

		Optional<ScheduledFlight> optional=scheduleFilghtDao.findById(scheduledFlightId);
		ScheduledFlight sFlight=optional.orElseThrow(()->new FlightNotFoundException("No Scheduled Flight with schedule ID : "+scheduledFlightId));
		sFlight.setAvailableSeats(sFlight.getAvailableSeats()-passengersCount);
		ScheduledFlight sFlight2=sFlight;
		//scheduleFilghtDao.deleteById(sFlight.getScheduleFlightId());
		scheduleFilghtDao.save(sFlight2);

	}

	public void modifyScheduledFlightAdd(BigInteger scheduledFlightId,int passengersCount) {
		// TODO Auto-generated method stub

		Optional<ScheduledFlight> optional=scheduleFilghtDao.findById(scheduledFlightId);
		ScheduledFlight sFlight=optional.orElseThrow(()->new FlightNotFoundException("No Scheduled Flight with schedule ID : "+scheduledFlightId));
		sFlight.setAvailableSeats(sFlight.getAvailableSeats()+passengersCount);
		ScheduledFlight sFlight2=sFlight;
		//scheduleFilghtDao.deleteById(sFlight.getScheduleFlightId());
		scheduleFilghtDao.save(sFlight2);

	}


	public boolean validateAttributes(Booking booking)
	{
		HashSet<BigInteger> pUIN=new HashSet<BigInteger>();

//		Passenger pList=booking.getPassengerList();
//		for(int i=0;i<pList.size();i++)
//		{
//			Passenger passenger=pList.get(i);
//			pUIN.add(passenger.getPassengerUIN());
//			if(passenger.getPassengerName().length()<3)
//			{
//				throw new BookingsExceptions("passenger Name must be 3 characters atleast");
//			}
//			else if(passenger.getPassengerUIN().compareTo(new BigInteger("100000000000"))<0 || passenger.getPassengerUIN().compareTo(new BigInteger("999999999999"))>0)
//			{
//				throw new BookingsExceptions("passenger UIN number should be 12 digits exactly");
//			}
//		}
//		if(pUIN.size()<booking.getNoOfPassangers())
//		{
//			throw new BookingsExceptions("passenger UIN numbers must be unique");
//		}
		return true;
	}



	public List<Booking> getByUserName(String userName) {
		// TODO Auto-generated method stub
		List<Booking> bookingList=(List<Booking>) bookingDao.getByUserName(userName);
		if(bookingList.isEmpty())
		{
			throw new FlightNotFoundException("No bookings Done by you");
		}
		return bookingList;
	}


}
